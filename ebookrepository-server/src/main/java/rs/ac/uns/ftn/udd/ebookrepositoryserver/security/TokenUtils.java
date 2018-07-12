package rs.ac.uns.ftn.udd.ebookrepositoryserver.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;
    
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            if(claims != null) {
            	username = claims.getSubject();
            } else {
            	username = null;
            }            
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    public Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
            	expirationDate = claims.getExpiration();
            } else {
            	expirationDate = null;
            }
        } catch (Exception e) {
        	expirationDate = null;
        }
        return expirationDate;
    }
    
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    
    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
    
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(generateCurrentDate());
    }

    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        SecurityUser user = (SecurityUser) userDetails;
        claims.put("sub", user.getEmail());
        claims.put("username", user.getEmail());
        claims.put("role", user.getAuthorities());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        return generateToken(claims);
    }
	
    private String generateToken(Map<String, Object> claims) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException ex) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
    	SecurityUser user = (SecurityUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !(isTokenExpired(token)));
    }

	
}
