package com.blinked.apis.responses;

import com.blinked.entities.dto.BaseRateDTO;

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
public class RateWithHasChildrenVO extends BaseRateDTO {

    private boolean hasChildren;
}
