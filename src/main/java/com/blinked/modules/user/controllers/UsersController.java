package com.blinked.modules.user.controllers;

import static com.blinked.modules.core.response.Responses.created;
import static com.blinked.modules.core.response.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.utils.HashIdsUtils;
import com.blinked.modules.core.utils.Page;
import com.blinked.modules.user.dtos.CreateUserProps;
import com.blinked.modules.user.dtos.UpdateUserProps;
import com.blinked.modules.user.dtos.UserInformation;
import com.blinked.modules.user.entities.User;
import com.blinked.modules.user.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Users")
@RequestMapping("/api/users")
public class UsersController {
	private final UserService userService;

	@Autowired
	public UsersController(UserService createService) {
		this.userService = createService;
	}

	@GetMapping
//	@SecurityRequirement(name = "token")
//	@PreAuthorize("hasAnyAuthority('ADM')")
	@Operation(summary = "Returns a list of users")
	public ResponseEntity<Page<UserInformation>> index(Optional<Integer> page, Optional<Integer> size) {
		Page<User> response = userService.find(page, size);
		return ok(response.map(UserInformation::new));
	}

	@GetMapping("/{user_id}")
//	@SecurityRequirement(name = "token")
	@PreAuthorize("hasAnyAuthority('ADM', 'USER')")
	@Operation(summary = "Show user info")
	public ResponseEntity<UserInformation> show(@PathVariable("user_id") String id) {
		User user = userService.find(HashIdsUtils.decode(id));
		return ok(new UserInformation(user));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	@Operation(summary = "Register a new user", description = "Returns the new user")
	public ResponseEntity<UserInformation> save(@Validated @RequestBody CreateUserProps props) {
		UserInformation user = new UserInformation(userService.create(props));
		return created(user, "api/users", user.getId());
	}

	@PutMapping("/{user_id}")
//	@SecurityRequirement(name = "token")
	@PreAuthorize("hasAnyAuthority('ADM', 'USER')")
	@Operation(summary = "Update user data")
	public ResponseEntity<UserInformation> update(@PathVariable("user_id") String id,
			@RequestBody @Validated UpdateUserProps body) {
		User user = userService.update(HashIdsUtils.decode(id), body);
		return ok(new UserInformation(user));
	}

	@DeleteMapping("/{user_id}")
	@ResponseStatus(NO_CONTENT)
//	@SecurityRequirement(name = "token")
	@Operation(summary = "Delete user")
	public void destroy(@PathVariable("user_id") String id) {
		userService.remove(HashIdsUtils.decode(id));
	}
}