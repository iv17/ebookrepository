package rs.ac.uns.ftn.udd.ebookrepositoryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;

public interface EBookRepository extends JpaRepository<EBook, Integer> {

}
