package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.CategoryConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.EBookConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.indexing.Indexer;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.elasticsearch.model.IndexUnit;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.CategoryService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.EBookService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.UserService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.EBookDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class EBookRepositoryController {

	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	EBookService eBookService;

	@Autowired
	CategoryConverter categoryConverter;

	@Autowired
	EBookConverter eBookConverter;
	
	@Autowired
	Indexer indexer;

	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<EBookDTO> getById(@PathVariable int id, Authentication authentication) {

		EBook eBook = eBookService.findById(id);
		EBookDTO response = eBookConverter.convert(eBook);

		IndexUnit indexUnit = new IndexUnit();
		for (IndexUnit i : indexer.findAll()) {
			if(i.getFilename().equals(response.getFilename())) {
				indexUnit = i;
			}
		}
		
		response.setHighlight(indexUnit.getHightlight());
		response.setText(indexUnit.getText());
		
		return new ResponseEntity<EBookDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/{categoryId}/category",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<EBookDTO>> getByCategory(@PathVariable int categoryId, Authentication authentication) {

		Category category = categoryService.findById(categoryId);

		List<EBook> ebooks = eBookService.findByCategory(category);

		List<EBookDTO> response = new ArrayList<>();
		for (EBook eBook : ebooks) {
			
			EBookDTO dto = eBookConverter.convert(eBook);
			IndexUnit indexUnit = new IndexUnit();
			for (IndexUnit i : indexer.findAll()) {
				if(i.getFilename().equals(eBook.getFilename())) {
					indexUnit = i;
				}
			}
			
			dto.setHighlight(indexUnit.getHightlight());
			dto.setText(indexUnit.getText());
			
			response.add(dto);
		}
		return new ResponseEntity<List<EBookDTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<EBookDTO>> getAll(Authentication authentication) {

		Iterable<EBook> eBooks = eBookService.findAll();

		List<EBookDTO> response = new ArrayList<>();
		for (EBook eBook : eBooks) {
			EBookDTO dto = eBookConverter.convert(eBook);
			IndexUnit indexUnit = new IndexUnit();
			for (IndexUnit i : indexer.findAll()) {
				if(i.getFilename().equals(eBook.getFilename())) {
					indexUnit = i;
				}
			}
			
			dto.setHighlight(indexUnit.getHightlight());
			dto.setText(indexUnit.getText());
			response.add(dto);
		}

		return new ResponseEntity<List<EBookDTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<EBookDTO> create(@RequestBody EBookDTO request, Authentication authentication) {

		User user = userService.findByEmail(authentication.getName());

		EBook eBook = eBookConverter.convert(request);

		eBook.setMime("PDF");
		eBook.setCataloguer(user);
		eBookService.save(eBook);

		EBookDTO response = eBookConverter.convert(eBook);
		
		IndexUnit indexUnit = new IndexUnit();
		for (IndexUnit i : indexer.findAll()) {
			if(i.getFilename().equals(response.getFilename())) {
				indexUnit = i;
			}
		}
		
		response.setHighlight(indexUnit.getHightlight());
		response.setText(indexUnit.getText());
		
		return new ResponseEntity<EBookDTO>(response, HttpStatus.OK);

	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<EBookDTO> update(@PathVariable int id, @RequestBody EBookDTO request, Authentication authentication) {

	
		EBook eBook = eBookService.findById(id); 
		EBook eBook2 = 	eBookConverter.update(eBook, request);
		eBookService.save(eBook2);

		EBookDTO response = eBookConverter.convert(eBook2);
		
		IndexUnit indexUnit = new IndexUnit();
		for (IndexUnit i : indexer.findAll()) {
			if(i.getFilename().equals(response.getFilename())) {
				indexUnit = i;
			}
		}
		
		response.setHighlight(indexUnit.getHightlight());
		response.setText(indexUnit.getText());
		
		return new ResponseEntity<EBookDTO>(response, HttpStatus.OK);

	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Void> delete(@PathVariable int id, Authentication authentication) {

		eBookService.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(
			value="download/{id}",
			method=RequestMethod.GET)
	public ResponseEntity<?> download(@PathVariable int id) throws IOException{

		EBook ebook = eBookService.findById(id);

		Path path = Paths.get(ebook.getFilename());
		if(new File(ebook.getFilename()).exists()) {

			byte[] content = Files.readAllBytes(path);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.setContentDispositionFormData(ebook.getFilename(), ebook.getFilename());
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

			return new ResponseEntity<byte[]>(content,headers,HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
