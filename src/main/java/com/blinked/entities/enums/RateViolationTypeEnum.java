package com.blinked.entities.enums;

import lombok.Getter;

/**
 * Comment violation type enumeration
 *
 * @author ssatwa
 * @date 2020/1/4
 */
@Getter
public enum RateViolationTypeEnum {
	/**
	 * Comment on the violation type
	 */
	NORMAL(0),
	/**
	 * Frequently
	 */
	FREQUENTLY(1);

	private final int type;

	RateViolationTypeEnum(int type) {
		this.type = type;
	}
}
