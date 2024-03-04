package com.blinked.apis;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.model.BaseResponse;
import com.blinked.apis.requests.AuthParam;
import com.blinked.apis.requests.AuthWithRefreshTokenParam;
import com.blinked.apis.requests.UserPropsParam;
import com.blinked.apis.responses.AuthVO;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.config.secuirty.CurrentUser;
import com.blinked.entities.User;
import com.blinked.services.AuthenticationService;
import com.blinked.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(name = "Public Authentication")
//@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
@RequestMapping("/api/authentication")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@Operation(summary = "Create a jwt token")
	public BaseResponse<AuthVO> create(@RequestBody @Valid AuthParam body) {
		return BaseResponse.ok(authService.create(body));
	}

	@PostMapping("/signup")
	@ResponseStatus(CREATED)
	@Operation(summary = "Register a new user", description = "Returns the new user")
	public BaseResponse<AuthVO> save(@Validated @RequestBody UserPropsParam props) {
		User user = userService.create(props);
		return BaseResponse.ok(authService.createToken(user.getId()));
	}

	@PostMapping("/signup-provider")
	@ResponseStatus(CREATED)
	@Operation(summary = "Provider SignIn", description = "Returns the new user")
	public BaseResponse<AuthVO> signupProvider(@Validated @RequestBody UserPropsParam props) {
		User user = userService.createForProvider(props);
		System.out.println("IIIMAGE:" + user.getImage());
		return BaseResponse.ok(authService.createToken(user.getId()));
	}

	@PostMapping("/refresh")
	@Operation(summary = "Create a new jwt token from refresh code")
	public BaseResponse<AuthVO> refresh(@RequestBody @Valid AuthWithRefreshTokenParam body) {
		return BaseResponse.ok(authService.create(body));
	}

	@GetMapping("/me")
	@Operation(summary = "Get User Dtls with Token")
	public BaseResponse<AuthVO> getCurrentUser(@CurrentUser AuthorizedUser authorized,
			@RequestHeader(value = "Authorization") String authorization) {
		return BaseResponse.ok(authService.createToken(authorized.getId()));
	}
}