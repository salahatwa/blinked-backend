package com.blinked.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthParam {
  @Schema(example = "ssatwa@gmail.com", required = true)
  @NotBlank(message = "Email can't be empty")
  @jakarta.validation.constraints.Email(message = "email invalido")
  private String email;
  
  @Schema(example = "password", required = true)
  @NotBlank(message = "Password can't be empty")
  private String password;
}
