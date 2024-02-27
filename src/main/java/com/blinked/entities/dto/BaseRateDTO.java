package com.blinked.entities.dto;

import java.util.Date;

import com.blinked.entities.BaseRate;
import com.blinked.entities.enums.RateStatus;
import com.blinked.repositories.base.OutputConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base comment output dto.
 *
 * @author ssatwa
 * @date 2019-03-20
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseRateDTO implements OutputConverter<BaseRateDTO, BaseRate> {

    private Long id;

    private String author;

    private String email;

    private String ipAddress;

    private String authorUrl;

    private String gravatarMd5;

    private String content;

    private RateStatus status;

    private String userAgent;

    private Long parentId;

    private Boolean isAdmin;

    private Boolean allowNotification;

    private Date createTime;

}
