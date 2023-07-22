package com.codewithdurgesh.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
