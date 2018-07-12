package rs.ac.uns.ftn.udd.ebookrepositoryserver.converters;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LoginRequestDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LoginResponseDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.RegisterRequestDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.UserDTO;

@Component
public class UserConverter {
	
	
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
	
		LoginResponseDTO dto = new LoginResponseDTO();
		dto.setUser(userDTO);
		dto.setToken(token);
		
		return dto;
	}
	
	//==================== REGISTER ====================
	public User convert(RegisterRequestDTO dto) {
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPassword(encoder.encode(dto.getPassword()));
		
		return user;
	}
	
	public UserDTO convert(User user) {
		UserDTO dto = new UserDTO();
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		
		return dto;
	}
	
}
