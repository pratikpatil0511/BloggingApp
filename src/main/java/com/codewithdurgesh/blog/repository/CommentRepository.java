package com.codewithdurgesh.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
