package com.codewithdurgesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.codewithdurgesh.blog.constants.ApiConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	   private String secret="jwtTokenKey";
	   
	   
	   public String getUsernameFromToken(String token) {
		   return getClaimFromToken(token,Claims::getSubject);	   
	   }
	   
	  public Date getExpirationDateFromToken(String token) {
		  return getClaimFromToken(token,Claims::getExpiration);
	  } 
	  
	  public<T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver) {
		  final Claims claims=getAllClaimsFromToken(token);
		  return claimsResolver.apply(claims);
	  }
	  
	  private Claims getAllClaimsFromToken(String token) {
		 return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); 
	  }
	  
	  private Boolean isTokenExpired(String token) {
		  final Date expiration=getExpirationDateFromToken(token);
		  return expiration.before(new Date());
	  }
	  
	  public String generateToken(UserDetails userDetails) {
		  Map<String,Object> claims=new HashMap<>();
		  return doGenerateToken(claims, userDetails.getUsername());
	  }
	  
	  //while creating the token -
	  //1.Define claims of the token,like Issuer,Expiration,Subject, and the ID
	  //2.Sign the JWT using the HS512 algorithm and secret key.
	  //3.According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	  // compaction of the JWT to a URL-safe String
	  
	  private String doGenerateToken(Map<String,Object> claims,String subject) {
		  return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				     .setExpiration(new Date(System.currentTimeMillis()+ApiConstants.JWT_TOKEN_VALIDITY*1000))
				     .signWith(SignatureAlgorithm.HS512,secret).compact();
				   }
	  
	  public Boolean validateToken(String token,UserDetails userDetails) {
		  final String username=getUsernameFromToken(token);
		  return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	  }
}