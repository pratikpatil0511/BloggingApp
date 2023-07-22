package com.codewithdurgesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.PostDto;
import com.codewithdurgesh.blog.services.FileService;
import com.codewithdurgesh.blog.services.PostService;
import com.codewithdurgesh.blog.utils.ApiResponse;
import com.codewithdurgesh.blog.utils.PostResponse;


@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}") //path from application.properties
	private String path;
	
	/**
	 * @author Pratik
	 * @apiNote create post with userid and categoryid mapping
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost
	(@RequestBody PostDto postDto,@PathVariable Long userId,@PathVariable Long categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	/**
	 * @author Pratik
	 * @apiNote get all posts related to user by using userid
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
		List<PostDto> allPostsByUser = this.postService.getAllPostsByUser(userId);
	
		return new ResponseEntity<List<PostDto>>(allPostsByUser,HttpStatus.OK);
		
	}
	
	/**
	 * @author Pratik
	 * @apiNote get all posts related to category by using categoryid
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId){
		List<PostDto> allPostsByCategory = this.postService.getAllPostsByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(allPostsByCategory,HttpStatus.OK);
	}
	
	/**
	 * @author Pratik
	 * @apiNote get all posts 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDirection
	 * @return
	 */
	@GetMapping("/") 
//requestmapping : ?pageNumber= & pageSize= we can use in postman directly no need to mention here in mapping
	public ResponseEntity <PostResponse> getAllPosts(
			
			@RequestParam(value="pageNumber",defaultValue =ApiConstants.DEFAULT_PAGE_NUMBER,required = false)  Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue =ApiConstants.DEFAULT_PAGE_SIZE,required = false)   Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = "postId",required = false) String sortBy,
			@RequestParam(value="sortDirection",defaultValue = "asc",required = false) String sortDirection
			                                         ){
		
		PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize,sortBy,sortDirection);
		
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	
	/**
	 * @author Pratik
	 * @apiNote get single post by using postid
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
		PostDto postById = this.postService.getPostById(id);
		
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	
	/**
	 * @author Pratik
	 * @apiNote delete post by using postid
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Long id){
		this.postService.deletePost(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(ApiConstants.DELETE_POST+id,true),HttpStatus.OK);
	}
	
	
	/**
	 * @author Pratik
	 * @apiNote update post details by using postid
	 * @param postId
	 * @param postDto
	 * @return
	 */
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePostById(@PathVariable Long postId,@RequestBody PostDto postDto){
		PostDto updatePost = this.postService.updatePost(postId, postDto);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	/**
	 * @author Pratik
	 * @apiNote get all posts by using title
	 * @param keyword
	 * @return
	 */
	@GetMapping("/searchtitle/{keywords}")
	public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable(value="keywords") String keyword){
		List<PostDto> postdtolist = this.postService.searchPostsByTitle(keyword);
		
		return new ResponseEntity<List<PostDto>>(postdtolist,HttpStatus.OK);
	}
	
	
	/**
	 * @author Pratik
	 * @apiNote get all posts by using content
	 * @param keyword
	 * @return
	 */
	@GetMapping("/searchcontent/{keywords}")
	public ResponseEntity<List<PostDto>> getPostByContent(@PathVariable(value="keywords") String keyword){
		List<PostDto> postdtolist = this.postService.searchPostByContent(keyword);
		
		return new ResponseEntity<List<PostDto>>(postdtolist,HttpStatus.OK);
	}
	
	
	/**
	 * @author Pratik
	 * @apiNote upload image for post by using postId
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Long postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postId, postDto);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.CREATED);
		
	}
	
	
	/**
	 * @author Pratik
	 * @apiNote get image by using imageName
	 * @param imageName
	 * @param response
	 * @throws IOException
	 */
	 @GetMapping(value = "/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
	        InputStream resource = this.fileService.getResource(path, imageName);
	        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	        StreamUtils.copy(resource,response.getOutputStream());
	    }
}
