package rs.ac.uns.ftn.udd.ebookrepositoryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findById(int id);
	Category findByName(String name);
	
}
