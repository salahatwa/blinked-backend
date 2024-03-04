package com.blinked.apis.requests;

import java.lang.reflect.ParameterizedType;

import com.api.common.model.InputConverter;
import com.api.common.utils.ReflectionUtils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Base meta param.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Data
public abstract class BaseMetaParam<META> implements InputConverter<META> {

	@NotBlank(message = "Product id cannot be empty")
	private Integer productId;

	@NotBlank(message = "Meta key cannot be empty")
	@Size(max = 1023, message = "The character length of the Meta key cannot exceed {max}")
	private String key;

	@NotBlank(message = "Meta value cannot be empty")
	@Size(max = 1023, message = "The character length of Meta value cannot exceed {max}")
	private String value;

	@Override
	public ParameterizedType parameterizedType() {
		return ReflectionUtils.getParameterizedTypeBySuperClass(BaseMetaParam.class, this.getClass());
	}
}
