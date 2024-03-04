package com.blinked.services;

import com.api.common.repo.CrudService;
import com.blinked.entities.ProductRateBlackList;
import com.blinked.entities.enums.RateViolationTypeEnum;

/**
 * Comment BlackList Service
 *
 * @author ssatwa
 * @date 2020/1/3
 */
public interface RateBlackListService extends CrudService<ProductRateBlackList, Long> {

	RateViolationTypeEnum ratesBanStatus(String ipAddress);
}
