package com.blinked.modules.core.model.vo;

import java.util.List;
import java.util.Map;

import com.blinked.modules.core.model.dto.CategoryDTO;
import com.blinked.modules.core.model.dto.TagDTO;
import com.blinked.modules.core.model.dto.post.BasePostSimpleDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Post list vo.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostListVO extends BasePostSimpleDTO {

	private Long commentCount;

	private List<TagDTO> tags;

	private List<CategoryDTO> categories;

	private Map<String, Object> metas;
}
