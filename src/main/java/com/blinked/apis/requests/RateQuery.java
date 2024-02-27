package com.blinked.apis.requests;

import com.blinked.entities.enums.RateStatus;

import lombok.Data;

/**
 * Comment query params.
 *
 * @author ssatwa
 * @date 2019/04/18
 */
@Data
public class RateQuery {

    /**
     * Keyword.
     */
    private String keyword;

    /**
     * Comment status.
     */
    private RateStatus status;
}
