package com.blinked.modules.core.model.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PostComment count projection
 *
 * @author ssatwa
 * @date 3/22/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCountProjection {

    private Long count;

    private Integer postId;
}
