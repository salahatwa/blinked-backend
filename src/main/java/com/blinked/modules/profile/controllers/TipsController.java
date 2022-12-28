package com.blinked.modules.profile.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Tips")
@RequestMapping("/api/tips-info")
public class TipsController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/is-applicable")
	@Operation(summary = "Display Tips or not")
	public Boolean isApplicable(@CurrentUser Authorized authorized) {

		User user = userRepository.getReferenceById(authorized.getId());

		if (user.getEducation() == null && user.getInformation() == null && user.getSkill() == null
				 && user.getWork() == null)
			return true;

		return false;

	}

}
