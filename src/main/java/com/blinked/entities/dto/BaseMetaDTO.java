package com.blinked.entities.dto;

import java.util.Date;

import com.api.common.model.ApiRs;
import com.api.common.model.OutputConverter;
import com.blinked.entities.BaseMeta;

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
public class BaseMetaDTO extends ApiRs implements OutputConverter<BaseMetaDTO, BaseMeta> {
	private String id;

	private Integer productId;

	private String key;

	private String value;

	private Date createTime;
}
