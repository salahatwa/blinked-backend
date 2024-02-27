package com.blinked.apis.admin;

import static com.blinked.constants.Responses.ok;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.apis.requests.UserPropsUpdateParam;
import com.blinked.apis.responses.RoleInfoVO;
import com.blinked.apis.responses.UserInfoVO;
import com.blinked.entities.User;
import com.blinked.repositories.RoleRepository;
import com.blinked.services.impl.UserServiceImpl;
import com.blinked.utils.HashIdsUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Users")
@RequestMapping("/api/users")
public class AdminController {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleRepository repository;

	@GetMapping("/list/roles")
	@Operation(summary = "Returns a list of roles")
	@PreAuthorize("hasAnyAuthority('ADM')")
	public ResponseEntity<List<RoleInfoVO>> listRoles() {
		return ok(repository.findAll().stream().map(RoleInfoVO::new).collect(Collectors.toList()));
	}

	@GetMapping("/list")
//	@SecurityRequirement(name = "token")
//	@PreAuthorize("hasAnyAuthority('ADM')")
	@Operation(summary = "Returns a list of users")
	public ResponseEntity<Page<UserInfoVO>> listUsers(
			@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Page<User> response = userService.listAll(pageable);

		return ok(response.map(UserInfoVO::new));
	}

	@GetMapping("/{user_id}")
//	@SecurityRequirement(name = "token")
//	@PreAuthorize("hasAnyAuthority('ADM', 'USER')")
	@Operation(summary = "Show user info")
	public ResponseEntity<UserInfoVO> show(@PathVariable("user_id") String id) {
		System.out.println(id);
		System.out.println(HashIdsUtils.decode(id));
		User user = userService.getById(HashIdsUtils.decode(id));
		return ok(new UserInfoVO(user));
	}

	@PutMapping("/{user_id}")
//	@SecurityRequirement(name = "token")
//	@PreAuthorize("hasAnyAuthority('ADM', 'USER')")
	@Operation(summary = "Update user data")
	public ResponseEntity<UserInfoVO> update(@PathVariable("user_id") String id,
			@RequestBody @Validated UserPropsUpdateParam body) {
		User user = userService.update(HashIdsUtils.decode(id), body);
		return ok(new UserInfoVO(user));
	}

	@DeleteMapping("/{user_id}")
	@ResponseStatus(NO_CONTENT)
//	@SecurityRequirement(name = "token")
	@Operation(summary = "Delete user")
	public void destroy(@PathVariable("user_id") String id) {
		userService.remove(HashIdsUtils.decode(id));
	}
}