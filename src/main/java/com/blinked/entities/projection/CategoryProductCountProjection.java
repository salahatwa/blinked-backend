package com.blinked.entities.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssatwa
 * @date 19-4-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProductCountProjection {

    private Long productCount;

    private String categoryId;
}
