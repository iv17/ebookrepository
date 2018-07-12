package rs.ac.uns.ftn.udd.ebookrepositoryserver.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.exceptions.NotFoundException;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	
    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtills;

    @Autowired
    private UserDetailsService userDetailsService;
    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);

        String username = tokenUtills.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (tokenUtills.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException e) {
            	throw new NotFoundException("User with that email doesn't exist!");
            }
        }

        chain.doFilter(request, response);
    }


}
