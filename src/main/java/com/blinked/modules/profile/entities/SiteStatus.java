package com.blinked.modules.profile.entities;

import com.blinked.modules.attachment.ValueEnum;

/**
 * Site status.
 *
 * @author ssatwa
 */
public enum SiteStatus implements ValueEnum<Integer> {

	/**
	 * Published status.
	 */
	PUBLISHED(0),

	/**
	 * Draft status.
	 */
	DRAFT(1),

	/**
	 * Recycle status.
	 */
	RECYCLE(2),

	/**
	 * Intimate status
	 */
	INTIMATE(3);

	private final int value;

	SiteStatus(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}
}
