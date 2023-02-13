package com.blinked.modules.core.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Tag with post count output dto.
 *
 * @author ssatwa
 * @date 3/20/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TagWithPostCountDTO extends TagDTO {

    private Long postCount;

}
