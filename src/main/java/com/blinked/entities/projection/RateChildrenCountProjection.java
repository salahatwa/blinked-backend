package com.blinked.entities.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssatwa
 * @date 19-5-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateChildrenCountProjection {

    private Long directChildrenCount;

    private String rateId;
}
