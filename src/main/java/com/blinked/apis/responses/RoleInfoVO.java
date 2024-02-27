package com.blinked.apis.responses;

import com.blinked.entities.Role;
import com.blinked.utils.HashIdsUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoleInfoVO {
	@Schema(example = "A1PLgjPPlM8x", required = true)
	private final String id;

	@Schema(example = "Administrador", required = true)
	private final String name;

	@Schema(example = "Administrador do sistema", required = true)
	private final String description;

	public RoleInfoVO(Role role) {
		this.id = HashIdsUtils.encode(role.getId());
		this.name = role.getName();
		this.description = role.getDescription();
	}
}
