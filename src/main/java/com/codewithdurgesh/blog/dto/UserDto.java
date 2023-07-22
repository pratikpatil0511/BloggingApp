package com.codewithdurgesh.blog.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//Validation:1.add dependency 2.add validation on field 3.add @valid annotation while inserting data

public class UserDto {

	
//we adding validation on field in this class,this class has connection with user	
	
	private Long userId;
	
	@Size(min = 3,max = 15,message = "userName must be in 3-15 range")
	@NotEmpty
	private String userName;
	
	@Email(message = "Email should be in a valid format")
	@NotEmpty
	private String userEmail;
	
	//^(starting with)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
			 message = "Password must match the required pattern")
	@JsonIgnore //now it will not show in postman
	private String userPassword;
	
	@NotEmpty
	private String aboutUser;
	
	private Set<RoleDto> roles=new HashSet<>();
}
