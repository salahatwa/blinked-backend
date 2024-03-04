package com.blinked.apis.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
	@jakarta.validation.constraints.Email(message = "{user.email.is-valid}")
	private String email;
}
