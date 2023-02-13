package com.blinked.modules.core.model.params;

import java.lang.reflect.ParameterizedType;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.blinked.modules.core.model.dto.base.InputConverter;
import com.blinked.modules.core.utils.ReflectionUtils;

import lombok.Data;

/**
 * Base Comment param.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-22
 */
@Data
public abstract class BaseCommentParam<COMMENT> implements InputConverter<COMMENT> {

	@NotBlank(message = "The commenter's nickname cannot be empty")
	@Size(max = 50, message = "The character length of the commenter's nickname cannot exceed {max}")
	private String author;

	@NotBlank(message = "The mailbox cannot be empty")
	@Email(message = "The email format is incorrect")
	@Size(max = 255, message = "The character length of the mailbox cannot exceed {max}")
	private String email;

	@Size(max = 255, message = "The character length of the commenter’s blog link cannot exceed {max}")
//	@URL(message = "Blog link format is incorrect")
	private String authorUrl;

	@NotBlank(message = "The comment content cannot be empty")
	@Size(max = 1023, message = "The character length of the comment content cannot exceed {max}")
	private String content;

	@Min(value = 1, message = "Post id must not be less than {value}")
	private Integer postId;

	@Min(value = 0, message = "PostComment parent id must not be less than {value}")
	private Long parentId = 0L;

	private Boolean allowNotification = true;

	@Override
	public ParameterizedType parameterizedType() {
		return ReflectionUtils.getParameterizedTypeBySuperClass(BaseCommentParam.class, this.getClass());
	}
}
