package com.blinked.apis.admin;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.api.common.model.BaseResponse;
import com.api.common.utils.HashIdsUtils;
import com.blinked.apis.requests.UserPropsUpdateParam;
import com.blinked.apis.responses.RoleInfoVO;
import com.blinked.apis.responses.UserInfoVO;
import com.blinked.entities.User;
import com.blinked.repositories.RoleRepository;
import com.blinked.services.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Admin Users")
@RequestMapping("/api/admin/users")
public class AdminUsersController {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/list/roles")
	@Operation(summary = "Returns a list of roles")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public BaseResponse<List<RoleInfoVO>> listRoles() {
		return BaseResponse.ok(roleRepository.findAll().stream().map(RoleInfoVO::new).collect(Collectors.toList()));
	}

	@GetMapping("/list/users")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "Returns a list of users")
	public BaseResponse<Page<UserInfoVO>> listUsers(
			@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
		Page<User> response = userService.listAll(pageable);

		return BaseResponse.ok(response.map(UserInfoVO::new));
	}

	@GetMapping("/{user_id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "Show user info")
	public BaseResponse<UserInfoVO> show(@PathVariable("user_id") String id) {
		User user = userService.getById(HashIdsUtils.decode(id));
		return BaseResponse.ok(new UserInfoVO(user));
	}

	@PutMapping("/{user_id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "Update user data")
	public BaseResponse<UserInfoVO> update(@PathVariable("user_id") String id,
			@RequestBody @Validated UserPropsUpdateParam body) {
		User user = userService.update(HashIdsUtils.decode(id), body);
		return BaseResponse.ok(new UserInfoVO(user));
	}

	@DeleteMapping("/{user_id}")
	@ResponseStatus(NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(summary = "Delete user")
	public void destroy(@PathVariable("user_id") String id) {
		userService.remove(HashIdsUtils.decode(id));
	}
}