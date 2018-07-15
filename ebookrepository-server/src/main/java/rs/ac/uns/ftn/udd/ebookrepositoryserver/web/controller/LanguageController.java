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

import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.LanguageConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Language;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.LanguageService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LanguageDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/languages")
public class LanguageController {

	@Autowired
	LanguageService languageService;

	@Autowired
	LanguageConverter languageConverter;
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<LanguageDTO> getById(@PathVariable int id, Authentication authentication) {

		Language language = languageService.findById(id);
		
		LanguageDTO response = languageConverter.convert(language);
		
		return new ResponseEntity<LanguageDTO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<LanguageDTO>> getAll(Authentication authentication) {

		List<LanguageDTO> response = new ArrayList<LanguageDTO>();

		for (Language language : languageService.findAll()) {
			response.add(languageConverter.convert(language));
		}
		return new ResponseEntity<List<LanguageDTO>>(response, HttpStatus.OK);
	}
	
}
