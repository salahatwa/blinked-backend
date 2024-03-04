package com.blinked.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.blinked.apis.responses.ProductRateWithProductVO;
import com.blinked.entities.ProductRate;

/**
 * Product Rate service interface.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
public interface ProductRateService extends BaseRateService<ProductRate> {

	/**
	 * Converts to with post vo.
	 *
	 * @param ratePage rate page must not be null
	 * @return a page of rate with post vo
	 */
	@NonNull
	Page<ProductRateWithProductVO> convertToWithProductVo(@NonNull Page<ProductRate> ratePage);

	/**
	 * Converts to with post vo
	 *
	 * @param rate rate
	 * @return a rate with post vo
	 */
	@NonNull
	ProductRateWithProductVO convertToWithProductVo(@NonNull ProductRate rate);

	/**
	 * Converts to with post vo
	 *
	 * @param postRates rate list
	 * @return a list of rate with post vo
	 */
	@NonNull
	List<ProductRateWithProductVO> convertToWithProductVo(@Nullable List<ProductRate> postRates);

	/**
	 * Validate RateBlackList Status
	 */
	void validateRateBlackListStatus();
}
