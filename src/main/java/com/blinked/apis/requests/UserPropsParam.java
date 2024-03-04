package com.blinked.apis.requests;

import static com.blinked.services.EmailValidations.validateEmailUniqueness;

import java.util.ArrayList;

import com.blinked.entities.Role;
import com.blinked.entities.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPropsParam {

	@Schema(example = "Salah Atwa")
	@NotEmpty(message = "${user.name.not-empty}")
	private String name;

	@Schema(example = "ssatwa@gmail.com")
	@NotEmpty(message = "{user.email.not-empty}")
	@jakarta.validation.constraints.Email(message = "{user.email.is-valid}")
	private String email;

	@Schema(example = "password")
	@NotEmpty(message = "{user.password.not-empty}")
	@Size(min = 8, max = 155, message = "{user.password.size}")
	private String password;

	@Schema(example = "Image URL")
	private String image;

	public void validate() {
		validateEmailUniqueness(email);
	}

	public User user() {
		return new User(name, email, password, this.image, new ArrayList<Role>());
	}
}
