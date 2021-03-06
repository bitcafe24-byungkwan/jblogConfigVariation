package com.cafe24.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import com.cafe24.security.AuthLoginInterceptor;
import com.cafe24.security.AuthLogoutInterceptor;
import com.cafe24.security.CheckValidInterceptor;

@Configuration
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurerAdapter {
	
	
	//
	// Argument Resolver
	//
//	@Bean
//	public AuthUserHndlerMethodArgumentResolver authUserHndlerMethodArgumentResolver() {
//		
//		return new AuthUserHndlerMethodArgumentResolver();
//	}	
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		argumentResolvers.add(authUserHndlerMethodArgumentResolver());
//	}
	
		
	//
	// Interceptor
	//
	@Bean
	public AuthLoginInterceptor authLoginInterceptor() {
		return new AuthLoginInterceptor();
	}
	@Bean
	public AuthLogoutInterceptor authLogoutInterceptor() {
		return new AuthLogoutInterceptor();
	}
	@Bean
	public CheckValidInterceptor checkValidInterceptor() {
		return new CheckValidInterceptor();
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authLoginInterceptor())
				.addPathPatterns("/user/auth");
		
		registry.addInterceptor(authLogoutInterceptor())
				.addPathPatterns("/user/logout");
		
		registry.addInterceptor(checkValidInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user/auth")
				.excludePathPatterns("/user/logout")
				.excludePathPatterns("/assets/**");				
	}
}
