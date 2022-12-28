package com.blinked.modules.profile.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.dtos.Profile;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.services.ConvertToFrontEndUser;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Profile")
@RequestMapping("/api/profile")
public class ProfileGetDtlsController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/dtls")
	@Transactional
	public Profile saveGraduation(@CurrentUser Authorized authorized) throws SQLException, IOException {

		User user = userRepository.getReferenceById(authorized.getId());

		return new ConvertToFrontEndUser().convertUserToProfile(user);
	}

}
