package com.blinked.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoveryParam {

  @Schema(example = "jubileu@email.com")
  @jakarta.validation.constraints.Email(message = "{recovery.email.is-valid}")
  @NotEmpty(message = "{recovery.email.not-empty}")
  private String email;
}
