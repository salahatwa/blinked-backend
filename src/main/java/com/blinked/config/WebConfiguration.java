package com.blinked.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("localhost:8100").allowedMethods("*").allowedHeaders("*");
	}

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Value("${spring.thymeleaf.prefix}")
//	private String resource;
//
//	@Value("${blinked.cloud}")
//	private String imgDir;
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		System.out.println(resource);
//		registry.addResourceHandler("/**").addResourceLocations(resource);
//		registry.addResourceHandler("/cloud/image/**").addResourceLocations(imgDir);
//	}

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//	    return web -> web.ignoring().requestMatchers(
//	            "/swagger-ui/**", "/v3/api-docs/**","/docs"
//	    );
//	}
}