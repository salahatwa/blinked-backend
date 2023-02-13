package com.blinked.modules.core.model.enums.converter;

import javax.persistence.Converter;

import com.blinked.modules.core.model.enums.DataType;

/**
 * Data type converter.
 *
 * @author ssatwa
 * @date 4/10/19
 */
@Converter(autoApply = true)
public class DataTypeConverter extends AbstractConverter<DataType, Integer> {

}
