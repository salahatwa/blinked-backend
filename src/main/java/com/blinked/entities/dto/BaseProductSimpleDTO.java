package com.blinked.entities.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base page simple output dto.
 *
 * @author ssatwa
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BaseProductSimpleDTO extends BaseProductMinimalDTO {

	private String summary;

	private String thumbnail;

	private Long visits;

	private Boolean disallowRate;

	private String password;

	private String template;

	private Integer topPriority;

	private Long likes;

	private Long wordCount;

	public boolean isTopped() {
		return this.topPriority != null && this.topPriority > 0;
	}
}
