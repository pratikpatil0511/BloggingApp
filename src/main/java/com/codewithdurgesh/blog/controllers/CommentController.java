package com.codewithdurgesh.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.CommentDto;
import com.codewithdurgesh.blog.services.CommentService;
import com.codewithdurgesh.blog.utils.ApiResponse;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	/**
	 * @author pratik
	 * @apiNote create comment using postId
	 * @param commentDto
	 * @param postId
	 * @return
	 */
	@PostMapping("/post/{postId}")
	ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Long postId){
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	/**
	 * @author Pratik
	 * @apiNote delete comment using commentId
	 * @param commentId
	 * @return
	 */
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId){
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(ApiConstants.DELETE_COMMENT+commentId,true),HttpStatus.OK);
	}
}
