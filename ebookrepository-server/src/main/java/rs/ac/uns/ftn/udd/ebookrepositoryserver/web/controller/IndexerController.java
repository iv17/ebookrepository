package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.Indexer;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.handlers.PDFHandler;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/indexer")
public class IndexerController {

	private static String DATA_DIR_PATH;
	private PDFHandler pdfHandler = new PDFHandler();
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

	@RequestMapping(
			value = "/upload",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<IndexUnit> upload(@RequestParam(value = "file") MultipartFile file) {

		if(!file.getContentType().equals("application/pdf")){
			return new ResponseEntity<IndexUnit>(HttpStatus.FORBIDDEN);
		}
		
		IndexUnit indexUnit = indexUploadedFile(file);
		
		return new ResponseEntity<IndexUnit>(indexUnit, HttpStatus.OK);

	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<IndexUnit> update(@RequestBody IndexUnit request, Authentication authentication) {
		
		IndexUnit indexUnit = new IndexUnit();
		for (IndexUnit i : indexer.findAll()) {
			if(i.getFilename().equals(request.getFilename())) {
				indexUnit = i;
			}
		}
		
		indexUnit.setTitle(request.getTitle());
		indexUnit.setAuthor(request.getAuthor());
		indexUnit.setKeywords(request.getKeywords());
		indexUnit.setPublicationYear(request.getPublicationYear());
		indexUnit.setMime(request.getMime());
		indexUnit.setCategoryName(request.getCategoryName());
		indexUnit.setLanguageName(request.getLanguageName());
		indexUnit.setHightlight("");
		String text = pdfHandler.getText(new File(request.getFilename()));
		indexUnit.setContent(text);
		indexer.update(indexUnit);
		
		System.out.println("Successfully indexed!");
		return new ResponseEntity<IndexUnit>(indexUnit, HttpStatus.OK);
	
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


}
