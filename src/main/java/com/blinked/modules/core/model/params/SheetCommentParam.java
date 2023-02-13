package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.entities.SheetComment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sheet comment param.
 *
 * @author ssatwa
 * @date 19-4-25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetCommentParam extends BaseCommentParam<SheetComment> {

}
