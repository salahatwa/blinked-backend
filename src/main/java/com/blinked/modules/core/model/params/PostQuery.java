package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.enums.PostStatus;

import lombok.Data;

/**
 * Post query.
 *
 * @author ssatwa
 * @date 4/10/19
 */
@Data
public class PostQuery {

	/**
	 * Keyword.
	 */
	private String keyword;

	/**
	 * Post status.
	 */
	private PostStatus status;

	/**
	 * Category id.
	 */
	private Integer categoryId;

}
