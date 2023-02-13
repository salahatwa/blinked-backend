package com.blinked.modules.core.model.vo;

import com.blinked.modules.core.model.dto.BaseCommentDTO;
import com.blinked.modules.core.model.dto.post.BasePostMinimalDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PostComment list with post vo.
 *
 * @author ssatwa
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SheetCommentWithSheetVO extends BaseCommentDTO {

	private BasePostMinimalDTO sheet;
}
