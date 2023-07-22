package com.codewithdurgesh.blog.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

	@Bean //this is used to create bean at method level
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
}
