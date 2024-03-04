package com.blinked.apis.responses;

import com.api.common.model.ApiRs;
import com.api.common.utils.HashIdsUtils;
import com.blinked.entities.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoleInfoVO extends ApiRs {
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
