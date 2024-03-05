package com.blinked.entities.dto;

import java.util.Date;

import com.api.common.model.ApiRs;
import com.api.common.model.OutputConverter;
import com.blinked.entities.BaseRate;
import com.blinked.entities.enums.RateStatus;

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
public class BaseRateDTO extends ApiRs implements OutputConverter<BaseRateDTO, BaseRate> {

	private String id;

	private String author;

	private String email;

	private String ipAddress;

	private String authorUrl;

	private String gravatarMd5;

	private String content;

	private RateStatus status;

	private String userAgent;

	private String parentId;

	private Boolean isAdmin;

	private Boolean allowNotification;

	private Date createTime;

}
