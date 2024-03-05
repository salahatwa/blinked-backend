package com.blinked.apis.requests;

import java.lang.reflect.ParameterizedType;

import com.api.common.model.InputConverter;
import com.api.common.utils.ReflectionUtils;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	@jakarta.validation.constraints.Email(message = "The email format is incorrect")
	@Size(max = 255, message = "The character length of the mailbox cannot exceed {max}")
	private String email;

	@Size(max = 255, message = "The character length of the rateer’s blog link cannot exceed {max}")
//	@URL(message = "Blog link format is incorrect")
	private String authorUrl;

	@NotBlank(message = "The rate content cannot be empty")
	@Size(max = 1023, message = "The character length of the rate content cannot exceed {max}")
	private String content;

	@Min(value = 1, message = "Product id must not be less than {value}")
	private Integer postId;

	@Min(value = 0, message = "Rate parent id must not be less than {value}")
	private String parentId = "";

	private Boolean allowNotification = true;

	@Override
	public ParameterizedType parameterizedType() {
		return ReflectionUtils.getParameterizedTypeBySuperClass(BaseRateParam.class, this.getClass());
	}
}
