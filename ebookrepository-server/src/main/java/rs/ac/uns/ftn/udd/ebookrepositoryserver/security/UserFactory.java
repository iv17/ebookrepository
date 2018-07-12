package rs.ac.uns.ftn.udd.ebookrepositoryserver.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;

public class UserFactory {

	
	private UserFactory() {
		super();
	}

	public static SecurityUser create(User user) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = user.getAuthorities();
        } catch (Exception e) {
            authorities = null;
        }
        
        SecurityUser securityUser = new SecurityUser();
        securityUser.setFirstName(user.getFirstName());
        securityUser.setLastName(user.getLastName());
        securityUser.setEmail(user.getEmail());
        securityUser.setPassword(user.getPassword());
        securityUser.setAuthorities(authorities);
        
        return securityUser;
	}
	
}