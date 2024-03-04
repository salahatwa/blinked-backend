package com.blinked.apis.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Journal query params.
 *
 * @author ssatwa
 * @date 2019/05/07
 */
@Data
public class MailParam {

	@NotBlank(message = "Recipient cannot be empty")
	@jakarta.validation.constraints.Email(message = "Email format error")
	private String to;

	@NotBlank(message = "The subject cannot be empty")
	private String subject;

	@NotBlank(message = "The content cannot be empty")
	private String content;
}
