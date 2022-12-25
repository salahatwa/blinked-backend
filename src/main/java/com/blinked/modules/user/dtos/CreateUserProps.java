package com.blinked.modules.user.dtos;

import static com.blinked.modules.core.validations.EmailValidations.validateEmailUniqueness;

import java.util.ArrayList;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blinked.modules.user.entities.Addressable;
import com.blinked.modules.user.entities.Role;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserProps implements Addressable {

	@Schema(example = "Salah Atwa")
	@NotEmpty(message = "${user.name.not-empty}")
	private String name;

	@Schema(example = "ssatwa@gmail.com")
	@NotEmpty(message = "{user.email.not-empty}")
	@Email(message = "{user.email.is-valid}")
	private String email;

	@Schema(example = "password")
	@NotEmpty(message = "{user.password.not-empty}")
	@Size(min = 8, max = 155, message = "{user.password.size}")
	private String password;

	public void validate() {
		validateEmailUniqueness(this);
	}

	public User user() {
		return new User(name, email, password, new ArrayList<Role>());
	}
}
