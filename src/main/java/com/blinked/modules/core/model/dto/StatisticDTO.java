package com.blinked.modules.core.model.dto;

import lombok.Data;

/**
 * Statistic DTO.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
public class StatisticDTO {

    private Long postCount;

    private Long commentCount;

    private Long categoryCount;

    @Deprecated
    private Long attachmentCount;

    private Long tagCount;

    private Long journalCount;

    private Long birthday;

    private Long establishDays;

    private Long linkCount;

    private Long visitCount;

    private Long likeCount;
}