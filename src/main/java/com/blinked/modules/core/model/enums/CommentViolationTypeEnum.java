package com.blinked.modules.core.model.enums;

import lombok.Getter;

/**
 * Comment violation type enumeration
 *
 * @author ssatwa
 * @date 2020/1/4
 */
@Getter
public enum CommentViolationTypeEnum {
	/**
	 * Comment on the violation type
	 */
	NORMAL(0),
	/**
	 * Frequently
	 */
	FREQUENTLY(1);

	private final int type;

	CommentViolationTypeEnum(int type) {
		this.type = type;
	}
}
