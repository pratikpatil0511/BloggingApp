package com.codewithdurgesh.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserEmail(String email); //we take email as username
}
