package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.enums.CommentStatus;

import lombok.Data;

/**
 * Comment query params.
 *
 * @author ssatwa
 * @date 2019/04/18
 */
@Data
public class CommentQuery {

	/**
	 * Keyword.
	 */
	private String keyword;

	/**
	 * Comment status.
	 */
	private CommentStatus status;
}
