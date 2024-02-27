package com.blinked.apis.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthParam {
  @Schema(example = "ssatwa@gmail.com", required = true)
  @NotBlank(message = "Email can't be empty")
  @Email(message = "email invalido")
  private String email;
  
  @Schema(example = "password", required = true)
  @NotBlank(message = "Password can't be empty")
  private String password;
}
