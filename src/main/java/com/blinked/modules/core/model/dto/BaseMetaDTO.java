package com.blinked.modules.core.model.dto;

import java.util.Date;

import com.blinked.modules.core.model.dto.base.OutputConverter;
import com.blinked.modules.core.model.entities.BaseMeta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base meta Dto.
 *
 * @author ssatwa
 * @date 2019-12-10
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseMetaDTO implements OutputConverter<BaseMetaDTO, BaseMeta> {
	private Long id;

	private Integer postId;

	private String key;

	private String value;

	private Date createTime;
}
