package com.blinked.apis.responses;

import com.blinked.entities.dto.BaseRateDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Base comment with parent comment vo.
 *
 * @author ssatwa
 * @date 3/31/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class BaseRateWithParentVO extends BaseRateDTO implements Cloneable {

    /**
     * Parent comment.
     */
    private BaseRateWithParentVO parent;

    @Override
    public BaseRateWithParentVO clone() {
        try {
            return (BaseRateWithParentVO) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("Clone not support exception", e);
            return null;
        }
    }
}
