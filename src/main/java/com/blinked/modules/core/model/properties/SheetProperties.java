package com.blinked.modules.core.model.properties;

import org.springframework.lang.NonNull;

/**
 * Sheet properties.
 *
 * @author ssatwa
 * @date 2020-02-11
 */
public enum SheetProperties implements PropertyEnum {

	/**
	 * Links page title.
	 */
	LINKS_TITLE("links_title", String.class, "Friendly Link"),

	/**
	 * Photos page title.
	 */
	PHOTOS_TITLE("photos_title", String.class, "Gallery"),

	/**
	 * Photos page size.
	 */
	PHOTOS_PAGE_SIZE("photos_page_size", Integer.class, "10"),

	/**
	 * Journals page title.
	 */
	JOURNALS_TITLE("journals_title", String.class, "Journal"),

	/**
	 * Journals page size.
	 */
	JOURNALS_PAGE_SIZE("journals_page_size", Integer.class, "10");

	private final String value;

	private final Class<?> type;

	private final String defaultValue;

	SheetProperties(String value, Class<?> type, String defaultValue) {
		this.value = value;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	@NonNull
	public String defaultValue() {
		return defaultValue;
	}
}
