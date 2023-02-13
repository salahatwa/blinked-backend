package com.blinked.modules.core.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

/**
 * Login param.
 *
 * @author ssatwa
 * @date 3/28/19
 */
@Data
@ToString
public class LoginParam {

	@NotBlank(message = "User name or email cannot be empty")
	@Size(max = 255, message = "The character length of the username or mailbox cannot exceed {max}")
	private String username;

	@NotBlank(message = "Login password cannot be empty")
	@Size(max = 100, message = "User password character length cannot exceed {max}")
	private String password;

	@Size(min = 6, max = 6, message = "The two-step verification code should be {max} bits")
	private String authcode;

}
