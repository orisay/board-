package com.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.common.interceptor.AdminCheckInterceptor;
import com.project.common.interceptor.LoginCheckInterceptor;
import com.project.common.interceptor.ManagerCheckInterceptor;
import com.project.common.interceptor.RateLimitInterceptor;
import com.project.common.interceptordto.AdminCheckInterceptorDTO;
import com.project.common.interceptordto.LoginCheckInterceptorDTO;
import com.project.common.interceptordto.ManagerCheckInterceptorDTO;

public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RateLimitInterceptor());

		AdminCheckInterceptorDTO adminList = getAdmin();
		ManagerCheckInterceptorDTO managerList = getManager();
		LoginCheckInterceptorDTO loginList = getLogin();
		registry.addInterceptor(new AdminCheckInterceptor()).addPathPatterns(adminList.getList());
		registry.addInterceptor(new ManagerCheckInterceptor()).addPathPatterns(managerList.getList());
		registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns(loginList.getList());
	}

	@Bean
	public RateLimitInterceptor rateLimitInterceptor() {
		return new RateLimitInterceptor(); // 새로 추가
	}

	@Bean
	@ConfigurationProperties(prefix = "cat")
	public AdminCheckInterceptorDTO getAdmin() {
		return new AdminCheckInterceptorDTO();
	}

	@Bean
	@ConfigurationProperties(prefix = "mng")
	public ManagerCheckInterceptorDTO getManager() {
		return new ManagerCheckInterceptorDTO();
	}

	@Bean
	@ConfigurationProperties(prefix = "login")
	public LoginCheckInterceptorDTO getLogin() {
		return new LoginCheckInterceptorDTO();
	}

}
