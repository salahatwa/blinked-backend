package com.blinked.entities.projection;

import com.blinked.entities.dto.CategoryDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category with post count dto.
 *
 * @author ssatwa
 * @date 19-4-23
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryWithProductCountProjection extends CategoryDTO {

    private Long productCount;
}
