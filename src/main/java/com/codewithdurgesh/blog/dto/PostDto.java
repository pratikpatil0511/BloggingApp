package com.codewithdurgesh.blog.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostDto {

	private Long postId;
	
	private String title;
	
	@NotBlank
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category; //we get and return data in Dto format so
	
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
}
