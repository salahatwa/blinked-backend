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
import com.blinked.modules.profile.repositories.TemplateRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Template")
@RequestMapping("/api/template")
public class TemplateController {

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	UserRepository userRepository;

	@PostMapping
	@Operation(summary = "Add Template To DB")
	public Template saveTemplateToSystem(@RequestBody Template template) {
		return templateRepository.save(template);
	}

	@PostMapping("/save-template-for-user/{templateId}")
	@Operation(summary = "Save Template to User")
	public Template saveTemplateForUser(@CurrentUser Authorized authorized,
			@PathVariable("templateId") Long templateId) {

		User user = userRepository.getReferenceById(authorized.getId());

		Template template = templateRepository.getReferenceById(templateId);

		user.setTemplate(template);

		userRepository.save(user);

		return template;
	}

	@GetMapping
	@Operation(summary = "Get Template By User Id")
	public Template getTemplateByUserId(@CurrentUser Authorized authorized) {
		return userRepository.getTemplateByUserId(authorized.getId());
	}

	@GetMapping("/list")
	@Operation(summary = "Get All Templates")
	public List<Template> getAllTemplates() {
		return templateRepository.findAll();
	}

}
