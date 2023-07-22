package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.dto.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,Long id);
	
	UserDto patchUpdateUser(UserDto userDto,Long id);
	
	UserDto getUserById(Long id);
	
	List<UserDto> getAllUsers();
	
	void deleteUserById(Long id);
	
	
}
