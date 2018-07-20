package rs.ac.uns.ftn.udd.ebookrepositoryserver.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.repository.EBookRepository;

@Service
public class EBookService {

	@Autowired
	EBookRepository eBookRepository;

	public EBook findById(int id) {
		return eBookRepository.findById(id);
	}
	public EBook findByFilename(String filename) {
		return eBookRepository.findByFilename(filename);
	}
	
	public List<EBook> findByCategory(Category category) {
		return eBookRepository.findByCategory(category);
	}

	public Iterable<EBook> findAll() {
		return eBookRepository.findAll();
	}

	public EBook save(EBook eBook) {
		return eBookRepository.save(eBook);
	}
	
	public File download(int id) {
		
		EBook eBook = eBookRepository.findById(id);
		
		File pdf = new File(eBook.getFilename());
		if(!pdf.exists()){
			return null;	
		}
		
		return pdf;
	}
	
}
