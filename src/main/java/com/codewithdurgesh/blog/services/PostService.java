package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.dto.PostDto;
import com.codewithdurgesh.blog.utils.PostResponse;

public interface PostService {

//create
	
	PostDto createPost(PostDto postDto,Long userId,Long categoryId);
	
//update
	
	PostDto updatePost(Long postId,PostDto postDto);
	
//delete
	
	void deletePost(Long postId);
	
//getAllPost
//By using pagination	
// we are returning PostResponse class to provide more data to user	
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String SortBy,String sortDirection);
	
//getSinglePost
	
	PostDto getPostById(Long postId);
	
//getAllPostsByCategory
	
	List<PostDto> getAllPostsByCategory(Long categoryId);
	
//getAllPostsByUser
	
	List<PostDto> getAllPostsByUser(Long userId);
	
//searchPostsByTitle
	
	List<PostDto> searchPostsByTitle(String keyword);
	
	
//searchPostsByContent
	
	List<PostDto> searchPostByContent(String contentkey);
}
