package com.codewithdurgesh.blog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codewithdurgesh.blog.security.CustomUserDetailsService;
import com.codewithdurgesh.blog.security.JwtAuthenticationEntryPoint;
import com.codewithdurgesh.blog.security.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //this is used to set authentication to perticular method/api/url
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	     http
	     .csrf().disable()
	     .authorizeHttpRequests()
	     .antMatchers("/api/auth/**").permitAll() //it allows all url starting with this
	               //here too we can use hasRole() instead of permitAll()
	     //.antMatchers(HttpMethod.GET).permitAll() //now we don't need to log in for all GET methods
	     .anyRequest()
	     .authenticated()
	     .and()
	     .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
	     .and()
	     .sessionManagement()
	     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	     
	     http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailsService).passwordEncoder(passwordEncode());
		
	}

	@Bean
	public PasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
				return super.authenticationManagerBean();
	}

	
	
}
