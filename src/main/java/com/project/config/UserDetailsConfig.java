package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.project.dto.MbSessionDTO;

@Configuration
public class UserDetailsConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		MbSessionDTO mbSessionDTO = SessionConfig.MbSessionDTO();
		UserDetails userDetails = User.builder()
				.username(mbSessionDTO.getId())
				.password(mbSessionDTO.getPw())
				.roles(mbSessionDTO.getRole())
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}
}
