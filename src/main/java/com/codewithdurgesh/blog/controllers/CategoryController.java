package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.CategoryDto;
import com.codewithdurgesh.blog.services.CategoryService;
import com.codewithdurgesh.blog.utils.ApiResponse;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
	private CategoryService categoryService;
    
    /**
     * @author Pratik
     * @apiNote create category
     * @param categoryDto
     * @return
     */
    @PostMapping(value="/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
    	
    	CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
    	
    	return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
    }
    
    /**
     * @author Pratik
     * @apiNote update category details by using id
     * @param categoryDto
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Long id){
    	
    	CategoryDto updatedCategory = this.categoryService.updateCategory(id, categoryDto);
    	
    	return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.CREATED);
    }
    
    /**
     * @author Pratik
     * @apiNote delete category by using id
     * @param id
     * @return
     */
    @DeleteMapping(value="/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
    	
    	this.categoryService.deleteCategory(id);
    	
    	return new ResponseEntity<ApiResponse>(new ApiResponse(ApiConstants.DELETE_CATEGORY+id,true),HttpStatus.OK);
    }
    
    /**
     * @author Pratik
     * @apiNote get single category by using id
     * @param cid
     * @return
     */
    @GetMapping(value="/{cid}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long cid){
    	
    	CategoryDto categoryById = this.categoryService.getCategoryById(cid);
    	
    	return new ResponseEntity<CategoryDto>(categoryById,HttpStatus.OK);
    }
    
    /**
     * @author Pratik
     * @apiNote get all categories
     * @return
     */
    @GetMapping(value="/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
    	
    	List<CategoryDto> allCategories = this.categoryService.getAllCategories();
    	
    	return ResponseEntity.ok(allCategories); //we can write like this also
    }
}
