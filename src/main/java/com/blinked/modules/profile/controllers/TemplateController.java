package com.blinked.modules.profile.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.entities.Template;
import com.blinked.modules.profile.repositories.TemplateRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.user.dtos.Authorized;

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
	@PreAuthorize("hasAnyAuthority('ADM')")
	public Template saveTemplateToSystem(@RequestBody Template template) {
		return templateRepository.save(template);
	}

//	@GetMapping
//	@Operation(summary = "Get Template By User Id")
//	public Template getTemplateByUserId(@CurrentUser Authorized authorized) {
//		return templateRepository.findTemplateByUserId(authorized.getId());
//	}

	@GetMapping("/list")
	@Operation(summary = "Get All Templates")
	public List<Template> getAllTemplates() {
		return templateRepository.findAll();
	}

}
