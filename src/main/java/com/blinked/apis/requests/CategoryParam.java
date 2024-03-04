package com.blinked.apis.requests;

import org.apache.commons.lang3.StringUtils;

import com.api.common.model.InputConverter;
import com.api.common.utils.SlugUtils;
import com.blinked.entities.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Category param.
 *
 * @author ssatwa
 * @date 2019-03-21
 */
@Data
public class CategoryParam implements InputConverter<Category> {

	@NotBlank(message = "The category name cannot be empty")
	@Size(max = 255, message = "The character length of the category name cannot exceed {max}")
	private String name;

	@Size(max = 255, message = "The character length of the category alias cannot exceed {max}")
	private String slug;

	@Size(max = 100, message = "The character length of the category description cannot exceed {max}")
	private String description;

	@Size(max = 1023, message = "The character length of the cover image link cannot exceed {max}")
	private String thumbnail;

	private Integer parentId = 0;

	@Override
	public Category convertTo() {

		slug = StringUtils.isBlank(slug) ? SlugUtils.slug(name) : SlugUtils.slug(slug);

		if (null == thumbnail) {
			thumbnail = "";
		}

		return InputConverter.super.convertTo();
	}

	@Override
	public void update(Category category) {

		slug = StringUtils.isBlank(slug) ? SlugUtils.slug(name) : SlugUtils.slug(slug);

		if (null == thumbnail) {
			thumbnail = "";
		}

		InputConverter.super.update(category);
	}
}
