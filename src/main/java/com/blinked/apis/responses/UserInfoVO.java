package com.blinked.apis.responses;

import java.util.List;

import com.api.common.model.ApiRs;
import com.api.common.utils.HashIdsUtils;
import com.blinked.entities.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserInfoVO extends ApiRs {

	@Schema(example = "BVnl07r1Joz3", required = true)
	private final String id;

	@Schema(example = "Jubileu da Silva", required = true)
	private final String name;

	@Schema(example = "jubileu@email.com", required = true)
	private final String email;

	@Schema(example = "image", required = false)
	private final String image;

	@Schema(example = "true", required = true)
	private Boolean active;

	@Schema(example = "[\"ADM\"]", required = true)
	private final List<String> roles;

	public UserInfoVO(User user) {
		this.id = HashIdsUtils.encode(user.getId());
		this.name = user.getName();
		this.email = user.getEmail();
		this.image = user.getImage();
		this.active = user.isActive();
		this.roles = user.getAuthorities();
	}
}
