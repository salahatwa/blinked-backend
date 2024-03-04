package com.blinked.entities.dto;

import java.util.Date;

import com.api.common.model.ApiRs;
import com.api.common.model.OutputConverter;
import com.blinked.entities.Category;

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
public class CategoryDTO extends ApiRs implements OutputConverter<CategoryDTO, Category> {

	private String id;

	private String name;

	private String slug;

	private String description;

	private String thumbnail;

	private Integer parentId;

	private Date createTime;

	private String fullPath;
}
