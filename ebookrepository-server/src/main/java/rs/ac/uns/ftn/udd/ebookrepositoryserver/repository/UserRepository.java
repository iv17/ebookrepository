package rs.ac.uns.ftn.udd.ebookrepositoryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findById(int id);

	User findByEmail(String email);
	
	User findByEmailAndPassword(String email, String password);
	
}
