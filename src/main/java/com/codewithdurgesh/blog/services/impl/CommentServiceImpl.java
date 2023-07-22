package com.codewithdurgesh.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.dto.CommentDto;
import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repository.CommentRepository;
import com.codewithdurgesh.blog.repository.PostRepository;
import com.codewithdurgesh.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Long postID) {
		Post post = this.postRepository.findById(postID)
				                       .orElseThrow(()->new ResourceNotFoundException(ApiConstants.POST_NOT_FOUND+postID));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepository.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		Comment comment = this.commentRepository.findById(commentId)
		                      .orElseThrow(()->new ResourceNotFoundException(ApiConstants.COMMENT_NOT_FOUND+commentId));
		
		this.commentRepository.delete(comment);
	}

}
