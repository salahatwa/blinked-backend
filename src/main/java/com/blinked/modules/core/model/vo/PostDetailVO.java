package com.blinked.modules.core.model.vo;

import java.util.List;
import java.util.Set;

import com.blinked.modules.core.model.dto.BaseMetaDTO;
import com.blinked.modules.core.model.dto.CategoryDTO;
import com.blinked.modules.core.model.dto.TagDTO;
import com.blinked.modules.core.model.dto.post.BasePostDetailDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Post vo.
 *
 * @author ssatwa
 * @date 2019-03-21
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostDetailVO extends BasePostDetailDTO {

	private Set<Integer> tagIds;

	private List<TagDTO> tags;

	private Set<Integer> categoryIds;

	private List<CategoryDTO> categories;

	private Set<Long> metaIds;

	private List<BaseMetaDTO> metas;
}
