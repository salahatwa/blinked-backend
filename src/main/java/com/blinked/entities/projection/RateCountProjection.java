package com.blinked.entities.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssatwa
 * @date 3/22/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateCountProjection {

    private Long count;

    private Integer productId;
}
