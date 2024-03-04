package com.blinked.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoveryUpdateParam {

  @Schema(example = "jubileu@email.com")
  @jakarta.validation.constraints.Email(message = "{recovery.email.is-valid}")
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
