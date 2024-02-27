package com.blinked.entities.dto;

import java.util.Date;

import com.blinked.entities.Category;
import com.blinked.repositories.base.OutputConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category output dto.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryDTO implements OutputConverter<CategoryDTO, Category> {

	private Integer id;

	private String name;

	private String slug;

	private String description;

	private String thumbnail;

	private Integer parentId;

	private Date createTime;

	private String fullPath;
}
