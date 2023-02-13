package com.blinked.modules.user.controllers;

import static com.blinked.modules.core.response.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.user.dtos.Authentication;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.dtos.CreateAuthenticationWithEmailAndPassword;
import com.blinked.modules.user.dtos.CreateAuthenticationWithRefreshToken;
import com.blinked.modules.user.dtos.CreateUserProps;
import com.blinked.modules.user.entities.User;
import com.blinked.modules.user.services.CreateAuthenticationService;
import com.blinked.modules.user.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Authentication")
@RequestMapping("/api/authentication")
public class AuthenticationController {
	private final CreateAuthenticationService service;
	private final UserService userService;

	@Autowired
	public AuthenticationController(CreateAuthenticationService service, UserService createService) {
		this.service = service;
		this.userService = createService;
	}

	@PostMapping("/login")
	@Operation(summary = "Create a jwt token")
	public ResponseEntity<Authentication> create(@RequestBody @Valid CreateAuthenticationWithEmailAndPassword body) {
		return ok(service.create(body));
	}

	@PostMapping("/signup")
	@ResponseStatus(CREATED)
	@Operation(summary = "Register a new user", description = "Returns the new user")
	public ResponseEntity<Authentication> save(@Validated @RequestBody CreateUserProps props) {
		User user = userService.create(props);	
		return ok(service.createToken(user.getId()));
	}

	@PostMapping("/refresh")
	@Operation(summary = "Create a new jwt token from refresh code")
	public ResponseEntity<Authentication> refresh(@RequestBody @Valid CreateAuthenticationWithRefreshToken body) {
		return ok(service.create(body));
	}

	@GetMapping("/me")
	@Operation(summary = "Get User Dtls with Token")
	public ResponseEntity<Authentication> getCurrentUser(@CurrentUser Authorized authorized,
			@RequestHeader(value = "Authorization") String authorization) {
		return ok(service.createToken(authorized.getId()));
	}
}