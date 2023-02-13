package com.blinked.modules.core.model.vo;

import com.blinked.modules.core.model.dto.BaseCommentDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Comment including replied count.
 *
 * @author ssatwa
 * @date 19-5-14
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentWithHasChildrenVO extends BaseCommentDTO {

	private boolean hasChildren;
}
