package com.blinked.modules.user.dtos;


import com.blinked.modules.core.utils.HashIdsUtils;
import com.blinked.modules.user.entities.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoleInformation {
  @Schema(example = "A1PLgjPPlM8x", required = true)
  private final String id;

  @Schema(example = "Administrador", required = true)
  private final String name;

  @Schema(example = "ADM", required = true)
  private final String shortName;

  @Schema(example = "Administrador do sistema", required = true)
  private final String description;

  public RoleInformation(Role role) {
    this.id = HashIdsUtils.encode(role.getId());
    this.name = role.getName();
    this.shortName = role.getShortName();
    this.description = role.getDescription();
  }
}
