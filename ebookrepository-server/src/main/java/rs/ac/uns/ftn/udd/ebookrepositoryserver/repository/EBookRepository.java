package rs.ac.uns.ftn.udd.ebookrepositoryserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;

public interface EBookRepository extends JpaRepository<EBook, Integer> {

	EBook findById(int id);
	EBook findByFilename(String filename);
	List<EBook> findByCategory(Category category);
	
}
