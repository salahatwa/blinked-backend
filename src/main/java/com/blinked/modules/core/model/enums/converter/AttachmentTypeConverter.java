package com.blinked.modules.core.model.enums.converter;

import javax.persistence.Converter;

import com.blinked.modules.core.model.enums.AttachmentType;

/**
 * Attachment type converter
 *
 * @author ssatwa
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class AttachmentTypeConverter extends AbstractConverter<AttachmentType, Integer> {

}
