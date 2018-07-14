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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.CategoryConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.CategoryService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.CategoryDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryConverter categoryConverter;
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<CategoryDTO> getById(@PathVariable int id, Authentication authentication) {

		Category category = categoryService.findById(id);
		
		CategoryDTO response = categoryConverter.convert(category);
		
		return new ResponseEntity<CategoryDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<CategoryDTO>> getAll(Authentication authentication) {

		List<CategoryDTO> response = new ArrayList<CategoryDTO>();

		for (Category c : categoryService.findAll()) {
			response.add(categoryConverter.convert(c));
		}
		return new ResponseEntity<List<CategoryDTO>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO request, Authentication authentication) {

		Category category = categoryConverter.convert(request);

		categoryService.save(category);
		CategoryDTO response = categoryConverter.convert(category);
		
		return new ResponseEntity<CategoryDTO>(response, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<CategoryDTO> update(@PathVariable("id") int id, @RequestBody CategoryDTO request, Authentication authentication) {

		Category category = categoryService.findById(id);
		category.setName(request.getName());
		categoryService.save(category);
		
		CategoryDTO response = categoryConverter.convert(category);
		return new ResponseEntity<CategoryDTO>(response, HttpStatus.CREATED);
	}
}
