package com.blinked.modules.core.model.enums.converter;

import javax.persistence.Converter;

import com.blinked.modules.core.model.enums.LogType;

/**
 * Log type converter.
 *
 * @author ssatwa
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class LogTypeConverter extends AbstractConverter<LogType, Integer> {

}
