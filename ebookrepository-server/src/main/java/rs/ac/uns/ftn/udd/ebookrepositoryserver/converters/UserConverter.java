package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.CategoryService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LoginRequestDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LoginResponseDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.UserDTO;

@Component
public class UserConverter {
	
	@Autowired
	private CategoryService categoryService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	//==================== LOGIN ====================
	public User convert(LoginRequestDTO dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(encoder.encode(dto.getPassword()));
		
		return user;
	}
	
	public LoginResponseDTO convert(User user, String token) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setType(user.getType());
		if(user.getCategory() != null) {
			userDTO.setCategoryName(user.getCategory().getName());
		}
		LoginResponseDTO dto = new LoginResponseDTO();
		dto.setUser(userDTO);
		dto.setToken(token);
		
		return dto;
	}
	
	//==================== REGISTER ====================	
	public UserDTO convert(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setType(user.getType());
		if(user.getCategory() != null) {
			dto.setCategoryName(user.getCategory().getName());
		}
		
		
		return dto;
	}
	
	public User convert(UserDTO dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(encoder.encode(dto.getPassword()));
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setType(dto.getType());
		if(dto.getCategoryName() != null) {
			user.setCategory(categoryService.findByName(dto.getCategoryName()));
		}
		
		
		return user;
	}
}
