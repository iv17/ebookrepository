package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.CategoryConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.EBookConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.CategoryService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.EBookService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.EBookDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class EBookRepositoryController {

	
	@Autowired
	CategoryService categoryService;

	@Autowired
	EBookService eBookService;
	
	@Autowired
	CategoryConverter categoryConverter;
	
	@Autowired
	EBookConverter eBookConverter;
	
	
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
			response.add(eBookConverter.convert(eBook));
		}
		return new ResponseEntity<List<EBookDTO>>(response, HttpStatus.OK);
	}
}
