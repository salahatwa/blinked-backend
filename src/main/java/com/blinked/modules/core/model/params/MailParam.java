package com.blinked.modules.core.model.params;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author ssatwa
 * @date 2019/05/07
 */
@Data
public class MailParam {

	@NotBlank(message = "Recipient cannot be empty")
	@Email(message = "Email format error")
	private String to;

	@NotBlank(message = "The subject cannot be empty")
	private String subject;

	@NotBlank(message = "The content cannot be empty")
	private String content;
}
