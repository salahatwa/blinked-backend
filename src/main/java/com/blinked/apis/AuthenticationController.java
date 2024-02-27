package com.blinked.apis;

import static com.blinked.constants.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.annotations.CurrentUser;
import com.blinked.apis.requests.AuthParam;
import com.blinked.apis.requests.AuthWithRefreshTokenParam;
import com.blinked.apis.requests.UserPropsParam;
import com.blinked.apis.responses.AuthVO;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.entities.User;
import com.blinked.services.CreateAuthenticationService;
import com.blinked.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(name = "Authentication")
@RequestMapping("/api/authentication")
public class AuthenticationController {
	@Autowired
	private CreateAuthenticationService service;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@Operation(summary = "Create a jwt token")
	public ResponseEntity<AuthVO> create(@RequestBody @Valid AuthParam body) {
		return ok(service.create(body));
	}

	@PostMapping("/signup")
	@ResponseStatus(CREATED)
	@Operation(summary = "Register a new user", description = "Returns the new user")
	public ResponseEntity<AuthVO> save(@Validated @RequestBody UserPropsParam props) {
		User user = userService.create(props);
		return ok(service.createToken(user.getId()));
	}

	@PostMapping("/signup-provider")
	@ResponseStatus(CREATED)
	@Operation(summary = "Provider SignIn", description = "Returns the new user")
	public ResponseEntity<AuthVO> signupProvider(@Validated @RequestBody UserPropsParam props) {
		User user = userService.createForProvider(props);
		System.out.println("IIIMAGE:" + user.getImage());
		return ok(service.createToken(user.getId()));
	}

	@PostMapping("/refresh")
	@Operation(summary = "Create a new jwt token from refresh code")
	public ResponseEntity<AuthVO> refresh(@RequestBody @Valid AuthWithRefreshTokenParam body) {
		return ok(service.create(body));
	}

	@GetMapping("/me")
	@Operation(summary = "Get User Dtls with Token")
	public ResponseEntity<AuthVO> getCurrentUser(@CurrentUser AuthorizedUser authorized,
			@RequestHeader(value = "Authorization") String authorization) {
		return ok(service.createToken(authorized.getId()));
	}
}