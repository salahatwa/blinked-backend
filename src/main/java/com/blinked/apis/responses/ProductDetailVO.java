package com.blinked.apis.responses;

import java.util.List;
import java.util.Set;

import com.blinked.entities.dto.BaseMetaDTO;
import com.blinked.entities.dto.BaseProductDetailDTO;
import com.blinked.entities.dto.CategoryDTO;
import com.blinked.entities.dto.TagDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Post vo.
 *
 * @author ssatwa
 * @author guqing
 * @date 2019-03-21
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductDetailVO extends BaseProductDetailDTO {

	private Set<String> tagIds;

	private List<TagDTO> tags;

	private Set<String> categoryIds;

	private List<CategoryDTO> categories;

	private Set<String> metaIds;

	private List<BaseMetaDTO> metas;

	private ProductDetailVO prevProduct;

	private ProductDetailVO nextProduct;
}
