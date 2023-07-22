package com.codewithdurgesh.blog.utils;

import java.util.List;

import com.codewithdurgesh.blog.dto.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostResponse {

	
	private List<PostDto> postDtoList;
	
	private int pageNumber;
	
	private int pageSize;
	
	private long totalElements;
	
	private int totalPages;
	
	private boolean lastPage;
}
