package com.codewithdurgesh.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithdurgesh.blog.constants.ApiConstants;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.repository.RoleRepository;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
	
		System.out.println(this.passwordEncoder.encode("xyz"));
		
		try
		{
			Role role=new Role();
			role.setId(ApiConstants.ADMIN_USER);
			role.setRoleName("ADMIN_USER");
			
			Role role2=new Role();
			role2.setId(ApiConstants.NORMAL_USER);
			role2.setRoleName("NORMAL_USER");
			
			List<Role> list = List.of(role,role2);
			
			List<Role> result = this.roleRepository.saveAll(list);
			
			result.forEach(r->System.out.println(r.getRoleName()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
