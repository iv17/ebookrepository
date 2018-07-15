package rs.ac.uns.ftn.udd.ebookrepositoryserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Language;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.repository.LanguageRepository;

@Service
public class LanguageService {

	@Autowired
	LanguageRepository languageRepository;

	public Language findById(int id) {
		return languageRepository.findById(id);
	}
	
	public Language findByName(String name) {
		return languageRepository.findByName(name);
	}

	public List<Language >findAll() {
		return languageRepository.findAll();
	}
	
	public Language save(Language category) {
		return languageRepository.save(category);
	}
	
}
