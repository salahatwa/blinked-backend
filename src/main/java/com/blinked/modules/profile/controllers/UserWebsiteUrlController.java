package com.blinked.modules.profile.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.entities.Template;
import com.blinked.modules.profile.entities.UserWebsiteUrl;
import com.blinked.modules.profile.repositories.TemplateRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.repositories.UserWebsiteUrlRepository;
import com.blinked.modules.user.dtos.Authorized;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Website Url")
@RequestMapping("/api/website-url")
public class UserWebsiteUrlController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserWebsiteUrlRepository userWebsiteUrlRepository;
	
	@Autowired
	TemplateRepository templateRepository;

	@PostMapping("/save-site-template-for-user/{templateId}")
	@Operation(summary = "Save User Website Url")
	public UserWebsiteUrl saveUserWebsiteUrl(@CurrentUser Authorized authorized,
			@RequestBody UserWebsiteUrl userWebsiteUrl,@PathVariable("templateId") Long templateId) {

		
		Template template = templateRepository.getReferenceById(templateId);
		
		userWebsiteUrl.setTemplate(template);
		userWebsiteUrl.setUserId(authorized.getId());
		userWebsiteUrl = userWebsiteUrlRepository.save(userWebsiteUrl);

		return userWebsiteUrl;
	}

	@GetMapping
	@Operation(summary = "Get User Websites Urls")
	public List<UserWebsiteUrl> getUserWebsiteUrl(@CurrentUser Authorized authorized) {

		return userWebsiteUrlRepository.getUserWebsiteUrl(authorized.getId());
	}

	@PostMapping("/check-url-exists")
	@Operation(summary = "Check If Website Url Exists")
	public Boolean checkIfUrlExists(@RequestBody String url) {

		System.out.println(url);
		UserWebsiteUrl userWebsiteUrl = userWebsiteUrlRepository.getIdFromUrl(url);

		System.out.println("::" + userWebsiteUrl.getUrl());
		if (userWebsiteUrl != null)
			return true;

		return false;
	}
}
