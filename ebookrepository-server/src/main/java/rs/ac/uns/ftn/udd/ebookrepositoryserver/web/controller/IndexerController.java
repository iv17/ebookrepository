package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.Indexer;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.EBookDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class IndexerController {

	private static String DATA_DIR_PATH;

	static {
		ResourceBundle rb=ResourceBundle.getBundle("application");
		DATA_DIR_PATH=rb.getString("dataDir");
	}

	@Autowired
	private Indexer indexer;

	@GetMapping("/reindex")
	public ResponseEntity<String> index() throws IOException {
		File dataDir = getResourceFilePath(DATA_DIR_PATH);
		long start = new Date().getTime();
		int numIndexed = indexer.index(dataDir);
		long end = new Date().getTime();
		String text = "Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds";
		return new ResponseEntity<String>(text, HttpStatus.OK);
	}

	private File getResourceFilePath(String path) {
		URL url = this.getClass().getClassLoader().getResource(path);
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			file = new File(url.getPath());
		}   
		return file;
	}

	@PostMapping("/index/add")
	public ResponseEntity<IndexUnit> multiUploadFileModel(@RequestParam(value = "file") MultipartFile file) {

		if(!file.getContentType().equals("application/pdf")){
			return new ResponseEntity<IndexUnit>(HttpStatus.FORBIDDEN);
		}
		
		IndexUnit indexUnit = indexUploadedFile(file);

		System.out.println(indexUnit.toString());
	
		System.out.println("Successfully uploaded!");
		return new ResponseEntity<IndexUnit>(indexUnit, HttpStatus.OK);

	}


	//save file
	private String saveUploadedFile(MultipartFile file) {
		String retVal = null;
		if (! file.isEmpty()) {
			byte[] bytes;
			try {
				bytes = file.getBytes();
				Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.write(path, bytes);
				retVal = path.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return retVal;
	}

	private IndexUnit indexUploadedFile(MultipartFile file) {

		String fileName = saveUploadedFile(file);
		if(fileName != null){
			IndexUnit indexUnit = indexer.getHandler(fileName).getIndexUnit(new File(fileName));
			indexer.add(indexUnit);
			
			return indexUnit;
		}
		return null;
	}

	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<EBookDTO>> getAll(Authentication authentication) {

		Iterable<IndexUnit> eBooks = indexer.findAll();
		
		List<EBookDTO> response = new ArrayList<>();
		for (IndexUnit indexUnit : eBooks) {
			EBookDTO dto = new EBookDTO();
			dto.setFilename(indexUnit.getFilename());
			dto.setTitle(indexUnit.getTitle());
			dto.setAuthor(indexUnit.getAuthor());
			dto.setKeywords(indexUnit.getKeywords());
			dto.setPublicationYear(indexUnit.getPublicationYear());
			dto.setMime(indexUnit.getMime());
			dto.setCategoryName(indexUnit.getCategoryName());
			dto.setLanguageName(indexUnit.getLanguageName());
			response.add(dto);
		}
		
		return new ResponseEntity<List<EBookDTO>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<IndexUnit> create(@RequestBody IndexUnit request, Authentication authentication) {
		
		IndexUnit indexUnit = indexer.findByFilename(request.getFilename());
		
		indexUnit.setTitle(request.getTitle());
		indexUnit.setAuthor(request.getAuthor());
		indexUnit.setKeywords(request.getKeywords());
		indexUnit.setPublicationYear(request.getPublicationYear());
		indexUnit.setMime(request.getMime());
		indexUnit.setCategoryName(request.getCategoryName());
		indexUnit.setLanguageName(request.getLanguageName());
		indexer.update(indexUnit);
		
		return new ResponseEntity<IndexUnit>(indexUnit, HttpStatus.OK);
	
	}

}
