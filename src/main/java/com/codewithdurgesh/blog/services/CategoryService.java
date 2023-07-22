package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.dto.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(Long categoryId,CategoryDto categoryDto);
	
	void deleteCategory(Long categoryId);
	
	CategoryDto getCategoryById(Long categoryId);
	
	List<CategoryDto> getAllCategories ();
}
