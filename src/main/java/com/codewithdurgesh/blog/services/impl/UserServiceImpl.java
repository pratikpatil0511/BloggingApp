package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.UserDto;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repository.RoleRepository;
import com.codewithdurgesh.blog.repository.UserRepository;
import com.codewithdurgesh.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

//modelMapper is used to convert UserDto to User and vice versa	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository; // this is field injection
	
	@Autowired
	private PasswordEncoder passwordEncoder;//for saving password in encoded format
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {

//converting userDto object into user object		
		User user = this.modelMapper.map(userDto, User.class);
		this.userRepository.save(user);

//converting user object into UserDto object		
		UserDto userDto2 = this.modelMapper.map(user, UserDto.class);
		return userDto2;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long id) {

		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND + id));

		user.setUserName(userDto.getUserName());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserEmail(userDto.getUserEmail());
		user.setAboutUser(userDto.getAboutUser());

		User updateduser = this.userRepository.save(user);
		return this.modelMapper.map(updateduser, UserDto.class);

	}

	@Override
	public UserDto getUserById(Long id) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND + id));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userlist = this.userRepository.findAll();

//here we have to get UserDto list so
		List<UserDto> userdtolist = userlist.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userdtolist;
	}

	@Override
	public void deleteUserById(Long id) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND + id));

		this.userRepository.delete(user);
	}

//for update userName,userPassword	
	@Override
	public UserDto patchUpdateUser(UserDto userDto, Long id) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND + id));

		user.setUserName(userDto.getUserName());
		user.setUserPassword(userDto.getUserPassword());

		User updateduser = this.userRepository.save(user);

		return this.modelMapper.map(updateduser, UserDto.class);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoding the password
		user.setUserPassword(this.passwordEncoder.encode(userDto.getUserPassword()));
		
		//role
		Role role = this.roleRepository.findById(ApiConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User saveduser = this.userRepository.save(user);
		
		return this.modelMapper.map(saveduser, UserDto.class);
	}

}
