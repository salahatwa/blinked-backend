package com.blinked.modules.core.model.enums;

/**
 * @author ssatwa
 * @date 2020-10-19
 */
public enum TimeUnit implements ValueEnum<Integer> {

	/**
	 * Days
	 */
	DAY(0),

	/**
	 * Hours
	 */
	HOUR(1);

	private final Integer value;

	TimeUnit(Integer value) {
		this.value = value;
	}

	/**
	 * Get enum value.
	 *
	 * @return enum value
	 */
	@Override
	public Integer getValue() {
		return value;
	}
}
