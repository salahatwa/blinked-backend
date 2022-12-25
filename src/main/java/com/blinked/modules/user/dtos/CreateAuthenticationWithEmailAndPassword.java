package com.blinked.modules.user.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuthenticationWithEmailAndPassword {
  @Schema(example = "ssatwa@gmail.com", required = true)
  @NotBlank(message = "Email can't be empty")
  @Email(message = "email invalido")
  private String email;
  
  @Schema(example = "password", required = true)
  @NotBlank(message = "Password can't be empty")
  private String password;
}
