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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.Indexer;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;

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


}
