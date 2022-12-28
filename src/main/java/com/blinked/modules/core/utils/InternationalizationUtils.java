package com.blinked.modules.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class InternationalizationUtils {

	public static MessageSource messageSource;

	@Autowired
	public InternationalizationUtils(MessageSource resourceBundleMessageSource) {
		InternationalizationUtils.messageSource = resourceBundleMessageSource;
	}

	public static String message(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

	public static String message(String key, Object... args) {
		return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
	}
}