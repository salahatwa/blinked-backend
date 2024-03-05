package com.blinked.apis.responses;

import java.util.Date;

import com.api.common.attachment.AttachmentType;
import com.api.common.model.ApiRs;
import com.api.common.model.OutputConverter;
import com.blinked.entities.Attachment;

import lombok.Data;

/**
 * Attachment output dto.
 *
 * @author ssatwa
 * @date 3/21/19
 */
@Data
public class AttachmentVO extends ApiRs implements OutputConverter<AttachmentVO, Attachment> {

	private String id;

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
