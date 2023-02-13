package com.blinked.modules.core.model.enums;

/**
 * Comment status.
 *
 * @author ssatwa
 */
public enum CommentStatus implements ValueEnum<Integer> {

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

	CommentStatus(Integer value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}
}
