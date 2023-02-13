package com.blinked.modules.core.model.params;

import com.blinked.modules.attachment.AttachmentType;

import lombok.Data;

/**
 * Attachment query params.
 *
 * @author ssatwa
 * @date 2019/04/18
 */
@Data
public class AttachmentQuery {

    /**
     * Keyword.
     */
    private String keyword;

    private String mediaType;

    private AttachmentType attachmentType;
}
