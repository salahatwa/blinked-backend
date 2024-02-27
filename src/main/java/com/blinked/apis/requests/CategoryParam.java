package com.blinked.apis.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.blinked.entities.Category;
import com.blinked.repositories.base.InputConverter;
import com.blinked.utils.SlugUtils;

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
