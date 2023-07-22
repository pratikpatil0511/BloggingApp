package com.codewithdurgesh.blog.services;

import com.codewithdurgesh.blog.dto.CommentDto;

public interface CommentService {

	
	CommentDto createComment(CommentDto commentDto,Long postID);
	
	void deleteComment(Long commentId);
}
