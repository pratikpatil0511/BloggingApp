package com.codewithdurgesh.blog.exceptions;


//this class is created to handle custom exception which extends RuntimeException



@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
		super(message);
		
	}

	
}
