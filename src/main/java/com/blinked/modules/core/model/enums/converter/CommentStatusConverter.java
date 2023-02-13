package com.blinked.modules.core.model.enums.converter;

import javax.persistence.Converter;

import com.blinked.modules.core.model.enums.CommentStatus;

/**
 * PostComment status converter.
 *
 * @author ssatwa
 * @date 3/27/19
 */
@Converter(autoApply = true)
public class CommentStatusConverter extends AbstractConverter<CommentStatus, Integer> {

}
