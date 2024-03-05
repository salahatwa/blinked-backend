package com.blinked.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.api.common.repo.CrudService;
import com.blinked.apis.requests.BaseRateParam;
import com.blinked.apis.requests.RateQuery;
import com.blinked.apis.responses.BaseRateVO;
import com.blinked.apis.responses.BaseRateWithParentVO;
import com.blinked.apis.responses.RateWithHasChildrenVO;
import com.blinked.entities.BaseRate;
import com.blinked.entities.dto.BaseRateDTO;
import com.blinked.entities.enums.RateStatus;

/**
 * Base rate service interface.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
public interface BaseRateService<RATE extends BaseRate> extends CrudService<RATE, String> {

	/**
	 * %d: parent rateator id %s: parent rateator author name %s: rate
	 * content
	 */
	@Deprecated
	String COMMENT_TEMPLATE = "<a href='#rate-id-%d'>@%s</a> %s";

	/**
	 * Lists rates by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of rate
	 */
	@NonNull
	List<RATE> listBy(@NonNull Integer productId);

	Page<RateWithHasChildrenVO> pageTopRatesBy(Integer targetId, RateStatus status, Pageable pageable);

	List<RATE> removeByProductId(Integer productId);

	/**
	 * Lists latest rates.
	 *
	 * @param top top number must not be less than 0
	 * @return a page of rates
	 */
	@NonNull
	Page<RATE> pageLatest(int top);

	/**
	 * Lists latest rates by status
	 *
	 * @param top    top number must not be less than 0
	 * @param status status
	 * @return a page of rates
	 */
	@NonNull
	Page<RATE> pageLatest(int top, @Nullable RateStatus status);

	/**
	 * Pages rates.
	 *
	 * @param status   rate status must not be null
	 * @param pageable page info must not be null
	 * @return a page of rate
	 */
	@NonNull
	Page<RATE> pageBy(@NonNull RateStatus status, @NonNull Pageable pageable);

	/**
	 * Pages rates.
	 *
	 * @param rateQuery rate query must not be null
	 * @param pageable     page info must not be null
	 * @return a page of rate
	 */
	@NonNull
	Page<RATE> pageBy(@NonNull RateQuery rateQuery, @NonNull Pageable pageable);

	/**
	 * Lists rate vos by product id.
	 *
	 * @param productId product id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of rate vo
	 */
	@NonNull
	Page<BaseRateVO> pageVosAllBy(@NonNull Integer productId, @NonNull Pageable pageable);

	/**
	 * Lists rate vos by product id.
	 *
	 * @param productId product id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of rate vo
	 */
	@NonNull
	Page<BaseRateVO> pageVosBy(@NonNull Integer productId, @NonNull Pageable pageable);

	/**
	 * Lists rate vos by list of COMMENT.
	 *
	 * @param rates rates must not be null
	 * @param pageable page info must not be null
	 * @return a page of rate vo
	 */
	@NonNull
	Page<BaseRateVO> pageVosBy(@NonNull List<RATE> rates, @NonNull Pageable pageable);

	/**
	 * Lists rate with parent vo.
	 *
	 * @param productId product id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of rate with parent vo.
	 */
	@NonNull
	Page<BaseRateWithParentVO> pageWithParentVoBy(@NonNull Integer productId, @NonNull Pageable pageable);

	/**
	 * Counts by rate status.
	 *
	 * @param status rate status must not be null
	 * @return rate count
	 */
	long countByStatus(@NonNull RateStatus status);

	/**
	 * Creates a rate by rate.
	 *
	 * @param rate rate must not be null
	 * @return created rate
	 */
	@NonNull
	@Override
	RATE create(@NonNull RATE rate);

	/**
	 * Creates a rate by rate param.
	 *
	 * @param rateParam commet param must not be null
	 * @return created rate
	 */
	@NonNull
	RATE createBy(@NonNull BaseRateParam<RATE> rateParam);

	/**
	 * Updates rate status.
	 *
	 * @param rateId rate id must not be null
	 * @param status    rate status must not be null
	 * @return updated rate
	 */
	@NonNull
	RATE updateStatus(@NonNull String rateId, @NonNull RateStatus status);

	/**
	 * Updates rate status by ids.
	 *
	 * @param ids    rate ids must not be null
	 * @param status rate status must not be null
	 * @return updated rates
	 */
	@NonNull
	List<RATE> updateStatusByIds(@NonNull List<String> ids, @NonNull RateStatus status);

	/**
	 * Removes rates in batch.
	 *
	 * @param ids ids must not be null.
	 * @return a list of deleted rate.
	 */
	@NonNull
	List<RATE> removeByIds(@NonNull Collection<String> ids);

	/**
	 * Converts to base rate dto.
	 *
	 * @param rate rate must not be null
	 * @return base rate dto
	 */
	@NonNull
	BaseRateDTO convertTo(@NonNull RATE rate);

	/**
	 * Converts to base rate dto list.
	 *
	 * @param rates rate list must not be null
	 * @return a list of base rate dto
	 */
	@NonNull
	List<BaseRateDTO> convertTo(@NonNull List<RATE> rates);

	/**
	 * Converts to base rate dto page.
	 *
	 * @param ratePage rate page must not be null
	 * @return a page of base rate dto
	 */
	@NonNull
	Page<BaseRateDTO> convertTo(@NonNull Page<RATE> ratePage);

	/**
	 * Converts to base rate vo tree.
	 *
	 * @param rates   rates list could be null
	 * @param comparator rate comparator could be null
	 * @return a rate vo tree
	 */
	List<BaseRateVO> convertToVo(@Nullable List<RATE> rates, @Nullable Comparator<BaseRateVO> comparator);

	/**
	 * Target validation.
	 *
	 * @param targetId target id must not be null (product id, sheet id or journal
	 *                 id)
	 */
	void validateTarget(@NonNull Integer targetId);

	/**
	 * Lists a page of top rate.
	 *
	 * @param targetId target id must not be null
	 * @param status   rate status must not be null
	 * @param pageable page info must not be null
	 * @return a page of top rate
	 */
	@NonNull
	Page<RateWithHasChildrenVO> pageTopProductsBy(@NonNull Integer targetId, @NonNull RateStatus status,
			@NonNull Pageable pageable);

	/**
	 * Lists children rates.
	 *
	 * @param targetId        target id must not be null
	 * @param rateParentId rate parent id must not be null
	 * @param status          rate status must not be null
	 * @param sort            sort info must not be null
	 * @return a list of children rate
	 */
	@NonNull
	List<RATE> listChildrenBy(@NonNull Integer targetId, @NonNull String rateParentId, @NonNull RateStatus status,
			@NonNull Sort sort);

	/**
	 * Lists children rates.
	 *
	 * @param targetId        target id must not be null
	 * @param rateParentId rate parent id must not be null
	 * @param sort            sort info must not be null
	 * @return a list of children rate
	 */
	@NonNull
	List<RATE> listChildrenBy(@NonNull Integer targetId, @NonNull String rateParentId, @NonNull Sort sort);

	/**
	 * Filters rate ip address.
	 *
	 * @param rate rate dto must not be null
	 */
	@Deprecated
	<T extends BaseRateDTO> T filterIpAddress(@NonNull T rate);

	/**
	 * Filters rate ip address.
	 *
	 * @param rates rate dto list
	 */
	@Deprecated
	<T extends BaseRateDTO> List<T> filterIpAddress(@Nullable List<T> rates);

	/**
	 * Filters rate ip address.
	 *
	 * @param ratePage rate page
	 */
	@Deprecated
	<T extends BaseRateDTO> Page<T> filterIpAddress(@NonNull Page<T> ratePage);

	/**
	 * Replace rate url in batch.
	 *
	 * @param oldUrl old blog url.
	 * @param newUrl new blog url.
	 * @return replaced rates.
	 */
	List<BaseRateDTO> replaceUrl(@NonNull String oldUrl, @NonNull String newUrl);

	Map<Integer, Long> countByProductIds(Collection<Integer> productIds);

	long countByProductId(Integer productId);

}
