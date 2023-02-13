package com.blinked.modules.core.model.vo;

import com.blinked.modules.core.model.dto.post.BasePostSimpleDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sheet list dto.
 *
 * @author ssatwa
 * @date 19-4-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetListVO extends BasePostSimpleDTO {

	private Long commentCount;
}
