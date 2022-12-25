package com.blinked.modules.user.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.blinked.modules.user.entities.Addressable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProps implements Addressable {

  @Schema(example = "Jubileu da Silva Sauro")
  @NotEmpty(message = "{user.name.not-empty}")
  private String name;
  
  @Schema(example = "jubileu.sauro@email.com")
  @NotEmpty(message = "{user.email.not-empty}")
  @Email(message = "{user.email.is-valid}")
  private String email;
}
