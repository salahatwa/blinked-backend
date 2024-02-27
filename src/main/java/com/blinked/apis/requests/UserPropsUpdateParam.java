package com.blinked.apis.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPropsUpdateParam {

	@Schema(example = "ssatwa")
	@NotEmpty(message = "{user.name.not-empty}")
	private String name;

	@Schema(example = "ssatwa@email.com")
	@NotEmpty(message = "{user.email.not-empty}")
	@Email(message = "{user.email.is-valid}")
	private String email;
}
