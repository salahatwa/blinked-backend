package com.blinked.modules.core.model.dto.post;

import java.util.Date;

import com.blinked.modules.core.model.dto.base.OutputConverter;
import com.blinked.modules.core.model.entities.BasePost;
import com.blinked.modules.core.model.enums.PostEditorType;
import com.blinked.modules.core.model.enums.PostStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base post minimal output dto.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
@ToString
@EqualsAndHashCode
public class BasePostMinimalDTO implements OutputConverter<BasePostMinimalDTO, BasePost> {

	private Integer id;

	private String title;

	private PostStatus status;

	private String slug;

	private PostEditorType editorType;

	private Date updateTime;

	private Date createTime;

	private Date editTime;

	private String metaKeywords;

	private String metaDescription;

	private String fullPath;
}
