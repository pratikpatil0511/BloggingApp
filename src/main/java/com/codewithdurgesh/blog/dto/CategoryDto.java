package com.codewithdurgesh.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {

	
	private Long categoryId;
	
	@NotBlank
	@Size(min=2,message = "categoryTitle must contains atleast 2 charaters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,message = "categoryDescription should be of atleast 10 characters")
	private String categoryDescription;
}
