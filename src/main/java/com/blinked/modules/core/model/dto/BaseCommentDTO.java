package com.blinked.modules.core.model.dto;

import java.util.Date;

import com.blinked.modules.core.model.dto.base.OutputConverter;
import com.blinked.modules.core.model.entities.BaseComment;
import com.blinked.modules.core.model.enums.CommentStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base comment output dto.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-20
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseCommentDTO implements OutputConverter<BaseCommentDTO, BaseComment> {

    private Long id;

    private String author;

    private String email;

    private String ipAddress;

    private String authorUrl;

    private String gravatarMd5;

    private String content;

    private CommentStatus status;

    private String userAgent;

    private Long parentId;

    private Boolean isAdmin;

    private Boolean allowNotification;

    private Date createTime;

}
