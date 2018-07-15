package rs.ac.uns.ftn.udd.ebookrepositoryserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Language;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.repository.EBookRepository;

@Service
public class EBookService {

	@Autowired
	EBookRepository eBookRepository;

	public EBook findById(int id) {
		return eBookRepository.findById(id);
	}
	
	/*public List<EBook> findByTitle(String title) {
		return eBookRepository.findByTitle(title);
	}
	
	public List<EBook> findByAuthor(String author) {
		return eBookRepository.findByAuthor(author);
	}
	
	public List<EBook> findByKeywords(String keywords) {
		return eBookRepository.findByKeywords(keywords);
	}
	
	public List<EBook> findByLanguage(Language language) {
		return eBookRepository.findByLanguage(language);
	}*/
	
	public List<EBook> findByCategory(Category category) {
		return eBookRepository.findByCategory(category);
	}

	public List<EBook> findAll() {
		return eBookRepository.findAll();
	}
	
}
