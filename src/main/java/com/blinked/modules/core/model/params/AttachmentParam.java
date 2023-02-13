package com.blinked.modules.core.model.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.blinked.modules.attachment.Attachment;
import com.blinked.modules.core.model.dto.base.InputConverter;

import lombok.Data;

/**
 * Attachment params.
 *
 * @author ssatwa
 * @date 2019/04/20
 */
@Data
public class AttachmentParam implements InputConverter<Attachment> {

	@NotBlank(message = "The attachment name cannot be empty")
	@Size(max = 255, message = "The character length of the attachment name cannot exceed {max}")
	private String name;

}
