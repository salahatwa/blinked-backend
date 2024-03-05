package com.blinked.entities.dto;

import java.util.Date;

import com.api.common.model.ApiRs;
import com.api.common.model.OutputConverter;
import com.blinked.entities.Tag;

import lombok.Data;

/**
 * Tag output dto.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
public class TagDTO extends ApiRs implements OutputConverter<TagDTO, Tag> {

	private String id;

	private String name;

	private String slug;

	private String thumbnail;

	private Date createTime;

	private String fullPath;
}
