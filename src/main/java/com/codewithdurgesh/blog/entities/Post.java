package com.codewithdurgesh.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	@Column(name="post_title")
	private String title;
	
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;

	
//mapping with Category	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	
//mapping  with User	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)  //foreign key will creeated only in comment table
	private Set<Comment> comments=new HashSet<>();
}
