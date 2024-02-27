package com.blinked.apis.requests;

import java.lang.reflect.ParameterizedType;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.blinked.repositories.base.InputConverter;
import com.blinked.utils.ReflectionUtils;

import lombok.Data;

/**
 * Base Comment param.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-22
 */
@Data
public abstract class BaseRateParam<RATE> implements InputConverter<RATE> {

	@NotBlank(message = "The rateer's nickname cannot be empty")
	@Size(max = 50, message = "The character length of the rateer's nickname cannot exceed {max}")
	private String author;

	@NotBlank(message = "The mailbox cannot be empty")
	@Email(message = "The email format is incorrect")
	@Size(max = 255, message = "The character length of the mailbox cannot exceed {max}")
	private String email;

	@Size(max = 255, message = "The character length of the rateer’s blog link cannot exceed {max}")
//	@URL(message = "Blog link format is incorrect")
	private String authorUrl;

	@NotBlank(message = "The rate content cannot be empty")
	@Size(max = 1023, message = "The character length of the rate content cannot exceed {max}")
	private String content;

	@Min(value = 1, message = "Post id must not be less than {value}")
	private Integer postId;

	@Min(value = 0, message = "PostComment parent id must not be less than {value}")
	private Long parentId = 0L;

	private Boolean allowNotification = true;

	@Override
	public ParameterizedType parameterizedType() {
		return ReflectionUtils.getParameterizedTypeBySuperClass(BaseRateParam.class, this.getClass());
	}
}
