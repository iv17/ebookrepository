package rs.ac.uns.ftn.udd.ebookrepositoryserver.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.converters.UserConverter;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.exceptions.BadRequestException;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.exceptions.NotFoundException;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.security.TokenUtils;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.UserService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.validation.UserValidationService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.ChangePasswordRequestDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LoginRequestDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.LoginResponseDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.UpdateUserRequestDTO;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.web.dto.UserDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserValidationService userValidationService;

	@Autowired
	UserConverter userConverter;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenUtils tokenUtils;

	@Value("${token.header}")
	private String tokenHeader;


	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO request) {

		userValidationService.validateIfEmailIsUnique(request.getEmail());

		User user = userConverter.convert(request);

		userService.save(user);

		UserDTO response =  userConverter.convert(user);

		return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);

	}

	@RequestMapping(
			value = "/login", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);


			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
			String token = this.tokenUtils.generateToken(userDetails);
			LoginResponseDTO response = new LoginResponseDTO();

			User user = userService.findByEmail(request.getEmail());
			UserDTO userDTO = userConverter.convert(user);
			response.setUser(userDTO);
			response.setToken(token);

			return new ResponseEntity<LoginResponseDTO>(response, HttpStatus.OK);

		} catch (BadCredentialsException e) {
			throw new NotFoundException("Wrong email or password!"); 
		}
	}

	@RequestMapping(value = "/logout",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> logout() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)){
			SecurityContextHolder.clearContext();
			return new ResponseEntity<String>("You are successfuly logged out!", HttpStatus.OK);
		} else {
			throw new BadRequestException("User is not authenticated!");
		}

	}

	@RequestMapping(
			value = "/change-password",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordRequestDTO request, Authentication authentication) {

		userValidationService.validateIfUserExist(authentication);

		User user = userService.findByEmail(authentication.getName());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if(request.getNewPassword().equals(request.getRepeatedNewPassword())) {
			user.setPassword(encoder.encode(request.getNewPassword()));
			userService.save(user);
			UserDTO response =  userConverter.convert(user);

			return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
		} else {
			UserDTO response =  userConverter.convert(user);
			return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
		}
	} 

	@RequestMapping(
			value = "/me",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<UserDTO> getMe(Authentication authentication) {

		userValidationService.validateIfUserExist(authentication);

		User user = userService.findByEmail(authentication.getName());

		UserDTO response =  userConverter.convert(user);

		return new ResponseEntity<UserDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.PUT
			)
	public ResponseEntity<UserDTO> update(@PathVariable int id, @RequestBody UpdateUserRequestDTO request, Authentication authentication) {

		userValidationService.validateIfUserExist(authentication);

		User user = userService.findById(id);
		
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());

		userService.save(user);
		UserDTO response =  userConverter.convert(user);

		return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
	} 
	
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<List<UserDTO>> getAll(Authentication authentication) {

		userValidationService.validateIfUserExist(authentication);

		List<User> users = userService.findAll();

		List<UserDTO> response = new ArrayList<>();
		for (User user : users) {
			response.add(userConverter.convert(user));
		}

		return new ResponseEntity<List<UserDTO>>(response, HttpStatus.OK);
	}

}
