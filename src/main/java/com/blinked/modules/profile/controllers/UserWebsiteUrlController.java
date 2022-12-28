package com.blinked.modules.profile.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.entities.UserWebsiteUrl;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.repositories.UserWebsiteUrlRepository;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

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

	@PostMapping
	@Operation(summary = "Save User Website Url")
	public UserWebsiteUrl saveUserWebsiteUrl(@CurrentUser Authorized authorized,
			@RequestBody UserWebsiteUrl userWebsiteUrl) {

		userWebsiteUrl = userWebsiteUrlRepository.save(userWebsiteUrl);

		User user = userRepository.getReferenceById(authorized.getId());

		user.setWebsiteUrl(userWebsiteUrl);

		userRepository.save(user);
		System.out.println(userWebsiteUrl.getUrl());
		return userWebsiteUrl;

	}

	@GetMapping
	@Operation(summary = "Get User Website Url")
	public UserWebsiteUrl getUserWebsiteUrl(@CurrentUser Authorized authorized) {

		return userRepository.getUserWebsiteUrl(authorized.getId());

	}

	@PostMapping("/check-url-exists")
	@Operation(summary = "Check If Website Url Exists")
	public Boolean checkIfUrlExists(@RequestBody String url) {

		UserWebsiteUrl userWebsiteUrl = userWebsiteUrlRepository.checkIfUrlExists(url);

		if (userWebsiteUrl != null)
			return true;

		return false;
	}
}
