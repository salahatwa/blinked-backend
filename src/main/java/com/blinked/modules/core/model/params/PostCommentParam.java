package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.entities.PostComment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PostComment param.
 *
 * @author ssatwa
 * @date 3/22/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostCommentParam extends BaseCommentParam<PostComment> {

}
