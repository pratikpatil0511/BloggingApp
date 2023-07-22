package com.codewithdurgesh.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.PostDto;
import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repository.CategoryRepository;
import com.codewithdurgesh.blog.repository.PostRepository;
import com.codewithdurgesh.blog.repository.UserRepository;
import com.codewithdurgesh.blog.services.PostService;
import com.codewithdurgesh.blog.utils.PostResponse;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto,Long userId,Long categoryId) {
		User user = this.userRepository.findById(userId)
				                  .orElseThrow(()->new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND+userId));
		
		Category category = this.categoryRepository.findById(categoryId)
		                  .orElseThrow(()->new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND+categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setUser(user);
		post.setCategory(category);
		post.setAddedDate(new Date()); //this will set current Date
		post.setImageName("default.png");
		
		Post savedPost = this.postRepository.save(post);
		
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(Long postDtoId, PostDto postDto) {
		Post post = this.postRepository.findById(postDtoId)
		                   .orElseThrow(()->new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND+postDtoId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post savedpost = this.postRepository.save(post);
		return this.modelMapper.map(savedpost, PostDto.class);
	}

	
	@Override
	public void deletePost(Long postDtoId) {
		Post post = this.postRepository.findById(postDtoId)
		                   .orElseThrow(()->new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND+postDtoId));
		this.postRepository.delete(post);
		
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection) {
//by ternary operation		
		Sort sort=sortDirection.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		
		/*
		 * Sort sort=null; if(sortDirection.equalsIgnoreCase("desc")) {
		 * sort=Sort.by(sortBy).descending(); }else { sort=Sort.by(sortBy).ascending();
		 * }
		 */
		
//pagination
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepository.findAll(pageRequest);//here passing Pageable object
		List<Post> content = pagePost.getContent(); //to get all Posts
		List<PostDto> postdtolist = content.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPostDtoList(postdtolist);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postId) {
		Post post = this.postRepository.findById(postId)
		                   .orElseThrow(()->new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND+postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
		                        .orElseThrow(()->new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND+categoryId));
		List<Post> postlist = this.postRepository.findByCategory(category);
		List<PostDto> postdtolist = postlist.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtolist;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Long userId) {
		User user = this.userRepository.findById(userId)
		                   .orElseThrow(()->new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND+userId));
		List<Post> postlist = this.postRepository.findByUser(user);
		List<PostDto> postdtolist = postlist.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtolist;
	}

	@Override
	public List<PostDto> searchPostsByTitle(String keyword) {
		List<Post> postlist = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postdtolist = postlist.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtolist;
	}

	@Override
	public List<PostDto> searchPostByContent(String contentkey) {
		List<Post> postlist = this.postRepository.searchByContent("%"+contentkey+"%");
		List<PostDto> postdtolist = postlist.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtolist;
	}

}
