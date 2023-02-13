package com.blinked.modules.core.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * User password param.
 *
 * @author ssatwa
 * @date 3/26/19
 */
@Data
public class PasswordParam {

	@NotBlank(message = "The old password cannot be empty")
	@Size(max = 100, message = "The character length of the password cannot exceed {max}")
	private String oldPassword;

	@NotBlank(message = "The new password cannot be empty")
	@Size(max = 100, message = "The character length of the password cannot exceed {max}")
	private String newPassword;

}
