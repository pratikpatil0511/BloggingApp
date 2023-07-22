package com.codewithdurgesh.blog.exceptions;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithdurgesh.blog.utils.ApiResponse;

//this class is to handle all exceptions

@RestControllerAdvice // (this annotation is used to handle exception globally)
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

		ApiResponse response = new ApiResponse();

		response.setMessage(ex.getMessage());
		response.setStatus(true);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);

	}

	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		 
		 Map<String,String> map=new HashMap<>();
		 
		 ex.getBindingResult().getAllErrors().forEach((error)->{
			 
			String fieldName= ((FieldError)error).getField();
			String message=error.getDefaultMessage();
			map.put(fieldName, message);
		 });
		 
		 return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
	 }

}

