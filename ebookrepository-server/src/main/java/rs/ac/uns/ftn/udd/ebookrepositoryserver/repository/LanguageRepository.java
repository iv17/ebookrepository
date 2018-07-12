package rs.ac.uns.ftn.udd.ebookrepositoryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

	Language findById(int id);
	Language findByName(String name);
	
}
