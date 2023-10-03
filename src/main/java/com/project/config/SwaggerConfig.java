package com.project.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private ApiInfo commonInfo() {
		return new ApiInfoBuilder().title("board").version("1.0").build();
	}

	private Set<String> getContentTypes() {
		Set<String> contentTypes = new HashSet<>();
		contentTypes.add("application/json;charset=UTF-8");
		contentTypes.add("application/x-www-form-urlencoded");
		return contentTypes;
	}

	private Docket createDocket(String groupName, String path) {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(getContentTypes())
				.produces(getContentTypes())
				.groupName(groupName).useDefaultResponseMessages(false)
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant(path))
				.build()
				.apiInfo(commonInfo());
	}

	@Bean
	public Docket boardApi() {
		return createDocket("board", "/project/board/**");
	}

	@Bean
	public Docket categoryApi() {
		return createDocket("cat", "/project/cat/**");
	}

	@Bean
	public Docket managerApi() {
		return createDocket("mng", "/project/mng/**");
	}

	@Bean
	public Docket mbApi() {
		return createDocket("member", "/project/mb/**");
	}

	@Bean
	public Docket replyApi() {
		return createDocket("reply", "/project/reply/**");
	}

}