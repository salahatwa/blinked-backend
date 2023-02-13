package com.blinked.modules.core.model.vo;

import java.util.List;

import com.blinked.modules.core.model.dto.CategoryDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category vo.
 *
 * @author ssatwa
 * @date 3/21/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryVO extends CategoryDTO {

	private List<CategoryVO> children;
}
