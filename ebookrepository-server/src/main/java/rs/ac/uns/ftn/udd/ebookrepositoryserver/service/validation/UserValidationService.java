package rs.ac.uns.ftn.udd.ebookrepositoryserver.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.exceptions.NotFoundException;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.UserService;

@Component
public class UserValidationService {

	@Autowired
	private UserService userService;
	
	String exception = "User with that email doesn't exist!";
	
	public void validateIfUserExist(Authentication authentication) {
		if(userService.findByEmail(authentication.getName()) == null) {
			throw new NotFoundException(exception);
		}
	}
	
	public void validateIfUserExist(String email) {
		if(userService.findByEmail(email) == null) {
			throw new NotFoundException(exception);
		}
	}
	
	public void validateIfEmailIsUnique(String email) {
		if(userService.findByEmail(email) != null) {
			throw new NotFoundException(exception);
		}
	}
}
