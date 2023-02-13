package com.blinked.modules.core.model.params;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Reset password params.
 *
 * @author ssatwa
 * @date 2019-09-05
 */
@Data
public class ResetPasswordParam {

	@NotBlank(message = "User name cannot be empty")
	private String username;

	@NotBlank(message = "The mailbox cannot be empty")
	private String email;

	private String code;

	private String password;
}
