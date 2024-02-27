package com.blinked.apis.responses;

import java.util.Date;

import com.blinked.attachment.AttachmentType;
import com.blinked.entities.Attachment;
import com.blinked.repositories.base.OutputConverter;

import lombok.Data;

/**
 * Attachment output dto.
 *
 * @author ssatwa
 * @date 3/21/19
 */
@Data
public class AttachmentVO implements OutputConverter<AttachmentVO, Attachment> {

	private Long id;

	private String name;

	private String path;

	private String fileKey;

	private String thumbPath;

	private String mediaType;

	private String suffix;

	private Integer width;

	private Integer height;

	private Long size;

	private AttachmentType type;

	private Date createdAt;
}
