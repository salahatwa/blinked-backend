package com.blinked.entities.dto;

import java.util.Date;

import com.api.common.model.ApiRs;
import com.api.common.model.OutputConverter;
import com.blinked.entities.BaseProduct;
import com.blinked.entities.enums.ProductStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base product minimal output dto.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseProductMinimalDTO extends ApiRs implements OutputConverter<BaseProductMinimalDTO, BaseProduct> {

	private Integer id;

	private String title;

	private ProductStatus status;

	private String slug;

	private Date updateTime;

	private Date createTime;

	private Date editTime;

	private String metaKeywords;

	private String metaDescription;

	private String template;
}
