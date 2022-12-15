package com.blinked;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

	@Value("${spring.thymeleaf.prefix}")
	private String resource;

//	"file:/C:/Users/alinma/Desktop/ForMyFather/backend/Blinked-template/templates/"
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(resource);
	}
}