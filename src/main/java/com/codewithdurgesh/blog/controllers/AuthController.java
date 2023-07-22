package com.codewithdurgesh.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.dto.UserDto;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.security.JwtTokenHelper;
import com.codewithdurgesh.blog.services.UserService;
import com.codewithdurgesh.blog.utils.JwtAuthRequest;
import com.codewithdurgesh.blog.utils.JwtAuthResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails username = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String generatedToken = this.jwtTokenHelper.generateToken(username);
		
		JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
		jwtAuthResponse.setToken(generatedToken);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken userAuthenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try {
		this.authenticationManager.authenticate(userAuthenticationToken);
		}
		catch(BadCredentialsException e) {
			System.out.println("Bad Credentials");
			throw new ResourceNotFoundException("Invalid Password");
		}
		
	}
	
	@PostMapping("/register")	
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}	
	
}
