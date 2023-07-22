package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.UserDto;
import com.codewithdurgesh.blog.services.UserService;
import com.codewithdurgesh.blog.utils.ApiResponse;

@RestController // @controller+@responseBody
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

//(for Document Comment /+**+enter)	
	
	/**
	 * @author Pratik
	 * @apiNote create user
	 * @param userDto
	 * @return
	 */
	
	
	@PostMapping(value = "/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto createUser = userService.createUser(userDto);

		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);

	}

	/**
	 * @author Pratik
	 * @apiNote get single user by using id
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

		UserDto userDto = userService.getUserById(id);

		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);

	}

	/**
	 * @author Pratik
	 * @apiNote get all users
	 * @return
	 */
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {

		List<UserDto> AllUserDto = userService.getAllUsers();

		return new ResponseEntity<List<UserDto>>(AllUserDto, HttpStatus.OK);
	}

	/**
	 * @author Pratik
	 * @apiNote delete user by using id
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")   //this url can be accessed by only those who has Role ADMIN
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id) {

		userService.deleteUserById(id);

		return new ResponseEntity<ApiResponse>(new ApiResponse(ApiConstants.DELETE_USER + id, true), HttpStatus.OK);
	}

	/**
	 * @author Pratik
	 * @apiNote update user details by using id
	 * @param id
	 * @param userDto
	 * @return
	 */
	@PutMapping("/users/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {

		UserDto updateUser = userService.updateUser(userDto, id);

		return new ResponseEntity<UserDto>(updateUser, HttpStatus.CREATED);
	}

	/**
	 * @author Pratik
	 * @apiNote update username and userpassword by using id
	 * @param id
	 * @param userDto
	 * @return
	 */
	@PatchMapping("users/{id}")
	public ResponseEntity<UserDto> updateUserPatch(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {

		UserDto patchUpdateUser = userService.patchUpdateUser(userDto, id);

		return new ResponseEntity<UserDto>(patchUpdateUser, HttpStatus.CREATED);
	}

}
