package com.blinked.entities.enums;

import com.blinked.utils.ValueEnum;

/**
 * Comment status.
 *
 * @author ssatwa
 */
public enum RateStatus implements ValueEnum<Integer> {

	/**
	 * Published
	 */
	PUBLISHED(0),

	/**
	 * Auditing status.
	 */
	AUDITING(1),

	/**
	 * Recycle Bin
	 */
	RECYCLE(2);

	private final Integer value;

	RateStatus(Integer value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}
}
