package com.blinked.modules.core.model.params;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.blinked.modules.core.model.dto.base.InputConverter;
import com.blinked.modules.core.model.support.CreateCheck;
import com.blinked.modules.core.model.support.UpdateCheck;
import com.blinked.modules.user.entities.User;

import lombok.Data;

/**
 * User param.
 *
 * @author ssatwa
 * @date 3/19/19
 */
@Data
public class UserParam implements InputConverter<User> {

	@NotBlank(message = "User name cannot be empty", groups = { CreateCheck.class, UpdateCheck.class })
	@Size(max = 50, message = "The character length of the user name cannot exceed {max}", groups = { CreateCheck.class,
			UpdateCheck.class })
	private String username;

	@NotBlank(message = "User nickname cannot be empty", groups = { CreateCheck.class, UpdateCheck.class })
	@Size(max = 255, message = "The character length of the user's nickname cannot exceed {max}", groups = {
			CreateCheck.class, UpdateCheck.class })
	private String nickname;

	@Email(message = "The format of the email address is incorrect", groups = { CreateCheck.class, UpdateCheck.class })
	@NotBlank(message = "Email address cannot be empty", groups = { CreateCheck.class, UpdateCheck.class })
	@Size(max = 127, message = "The character length of the email cannot exceed {max}", groups = { CreateCheck.class,
			UpdateCheck.class })
	private String email;

	@Null(groups = UpdateCheck.class)
	@Size(min = 8, max = 100, message = "The character length of the password must be between {min}-{max}", groups = {
			CreateCheck.class })
	private String password;

	@Size(max = 1023, message = "The character length of the avatar link address cannot exceed {max}", groups = {
			CreateCheck.class, UpdateCheck.class })
	private String avatar;

	@Size(max = 1023, message = "The character length of the user description cannot exceed {max}", groups = {
			CreateCheck.class, UpdateCheck.class })
	private String description;

}
