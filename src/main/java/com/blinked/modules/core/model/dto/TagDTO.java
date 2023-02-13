package com.blinked.modules.core.model.dto;

import java.util.Date;

import com.blinked.modules.core.model.dto.base.OutputConverter;
import com.blinked.modules.core.model.entities.Tag;

import lombok.Data;

/**
 * Tag output dto.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
public class TagDTO implements OutputConverter<TagDTO, Tag> {

	private Integer id;

	private String name;

	private String slug;

	private String thumbnail;

	private Date createTime;

	private String fullPath;
}
