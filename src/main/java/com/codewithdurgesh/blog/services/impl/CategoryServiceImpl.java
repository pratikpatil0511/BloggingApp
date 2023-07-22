package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.CategoryDto;
import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repository.CategoryRepository;
import com.codewithdurgesh.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = this.modelMapper.map(categoryDto, Category.class);

		Category savedCategory = this.categoryRepository.save(category);
		
		return this.modelMapper.map(savedCategory, CategoryDto.class);

	}

	@Override
	public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
		Category category = this.categoryRepository.findById(categoryId)
		                       .orElseThrow(()->new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND+categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepository.save(category);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
		                       .orElseThrow(()->new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND+categoryId));

		this.categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
		                       .orElseThrow(()->new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND+categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categorylist = this.categoryRepository.findAll();
		
		List<CategoryDto> categoryDtolist = categorylist.stream().map((category)->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtolist;
	}

}
