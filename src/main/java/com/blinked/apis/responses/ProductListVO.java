package com.blinked.apis.responses;

import java.util.List;
import java.util.Map;

import com.blinked.entities.dto.BaseProductSimpleDTO;
import com.blinked.entities.dto.CategoryDTO;
import com.blinked.entities.dto.TagDTO;

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
public class ProductListVO extends BaseProductSimpleDTO {

    private Long commentCount;

    private List<TagDTO> tags;

    private List<CategoryDTO> categories;

    private Map<String, Object> metas;
}
