package com.codewithdurgesh.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Long> {

//custom methods (we are taking argument other than Post)
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title); //here 'Title' is a field (title) from Post entity class
	                         //at back it will run 'like' query (SELECT * FROM Customers WHERE CustomerName LIKE '%a');
	
	//if above method gets error then we can use method with our own query ex
	
	@Query("select p from Post p where p.content like:key") //'Post' entity class name
	List<Post> searchByContent(@Param(value="key")String keyword);
}
