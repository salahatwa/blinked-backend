package com.blinked.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.hutool.core.lang.Console;

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
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("error-messages/messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Value("${spring.thymeleaf.prefix}")
	private String resource;
	
	@Value("${blinked.cloud}")
	private String imgDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println(resource);
		registry.addResourceHandler("/**").addResourceLocations(resource);
		 registry.addResourceHandler("/cloud/image/**").addResourceLocations(imgDir);
	}
}