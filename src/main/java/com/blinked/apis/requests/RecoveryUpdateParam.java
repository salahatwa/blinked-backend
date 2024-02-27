package com.blinked.apis.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class RecoveryUpdateParam {

  @Schema(example = "jubileu@email.com")
  @Email(message = "{recovery.email.is-valid}")
  @NotEmpty(message = "{recovery.email.not-empty}")
  private String email;

  @Schema(example = "5894")
  @NotEmpty(message = "{recovery.code.not-empty}")
  private String code;
  
  @Schema(example = "veryStrongAndSecurePassword")
  @NotEmpty(message = "{user.password.not-empty}")
  @Size(min = 8, max = 155, message = "{user.password.size}")
  private String password;
}
