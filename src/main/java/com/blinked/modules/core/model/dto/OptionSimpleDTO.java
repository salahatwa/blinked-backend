package com.blinked.modules.core.model.dto;

import java.util.Date;

import com.blinked.modules.core.model.enums.OptionType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Option list output dto.
 *
 * @author ssatwa
 * @date 2019-12-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OptionSimpleDTO extends OptionDTO {

	private Integer id;

	private OptionType type;

	private Date createTime;

	private Date updateTime;
}
