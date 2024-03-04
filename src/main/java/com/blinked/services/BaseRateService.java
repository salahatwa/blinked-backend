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
 * Base comment service interface.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
public interface BaseRateService<RATE extends BaseRate> extends CrudService<RATE, Long> {

	/**
	 * %d: parent commentator id %s: parent commentator author name %s: comment
	 * content
	 */
	@Deprecated
	String COMMENT_TEMPLATE = "<a href='#comment-id-%d'>@%s</a> %s";

	/**
	 * Lists comments by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of comment
	 */
	@NonNull
	List<RATE> listBy(@NonNull Integer productId);

	Page<RateWithHasChildrenVO> pageTopRatesBy(Integer targetId, RateStatus status, Pageable pageable);

	List<RATE> removeByProductId(Integer postId);

	/**
	 * Lists latest comments.
	 *
	 * @param top top number must not be less than 0
	 * @return a page of comments
	 */
	@NonNull
	Page<RATE> pageLatest(int top);

	/**
	 * Lists latest comments by status
	 *
	 * @param top    top number must not be less than 0
	 * @param status status
	 * @return a page of comments
	 */
	@NonNull
	Page<RATE> pageLatest(int top, @Nullable RateStatus status);

	/**
	 * Pages comments.
	 *
	 * @param status   comment status must not be null
	 * @param pageable page info must not be null
	 * @return a page of comment
	 */
	@NonNull
	Page<RATE> pageBy(@NonNull RateStatus status, @NonNull Pageable pageable);

	/**
	 * Pages comments.
	 *
	 * @param commentQuery comment query must not be null
	 * @param pageable     page info must not be null
	 * @return a page of comment
	 */
	@NonNull
	Page<RATE> pageBy(@NonNull RateQuery commentQuery, @NonNull Pageable pageable);

	/**
	 * Lists comment vos by product id.
	 *
	 * @param productId product id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of comment vo
	 */
	@NonNull
	Page<BaseRateVO> pageVosAllBy(@NonNull Integer productId, @NonNull Pageable pageable);

	/**
	 * Lists comment vos by product id.
	 *
	 * @param productId product id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of comment vo
	 */
	@NonNull
	Page<BaseRateVO> pageVosBy(@NonNull Integer productId, @NonNull Pageable pageable);

	/**
	 * Lists comment vos by list of COMMENT.
	 *
	 * @param comments comments must not be null
	 * @param pageable page info must not be null
	 * @return a page of comment vo
	 */
	@NonNull
	Page<BaseRateVO> pageVosBy(@NonNull List<RATE> comments, @NonNull Pageable pageable);

	/**
	 * Lists comment with parent vo.
	 *
	 * @param productId product id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of comment with parent vo.
	 */
	@NonNull
	Page<BaseRateWithParentVO> pageWithParentVoBy(@NonNull Integer productId, @NonNull Pageable pageable);

	/**
	 * Counts by comment status.
	 *
	 * @param status comment status must not be null
	 * @return comment count
	 */
	long countByStatus(@NonNull RateStatus status);

	/**
	 * Creates a comment by comment.
	 *
	 * @param comment comment must not be null
	 * @return created comment
	 */
	@NonNull
	@Override
	RATE create(@NonNull RATE comment);

	/**
	 * Creates a comment by comment param.
	 *
	 * @param commentParam commet param must not be null
	 * @return created comment
	 */
	@NonNull
	RATE createBy(@NonNull BaseRateParam<RATE> commentParam);

	/**
	 * Updates comment status.
	 *
	 * @param commentId comment id must not be null
	 * @param status    comment status must not be null
	 * @return updated comment
	 */
	@NonNull
	RATE updateStatus(@NonNull Long commentId, @NonNull RateStatus status);

	/**
	 * Updates comment status by ids.
	 *
	 * @param ids    comment ids must not be null
	 * @param status comment status must not be null
	 * @return updated comments
	 */
	@NonNull
	List<RATE> updateStatusByIds(@NonNull List<Long> ids, @NonNull RateStatus status);

	/**
	 * Removes comments in batch.
	 *
	 * @param ids ids must not be null.
	 * @return a list of deleted comment.
	 */
	@NonNull
	List<RATE> removeByIds(@NonNull Collection<Long> ids);

	/**
	 * Converts to base comment dto.
	 *
	 * @param comment comment must not be null
	 * @return base comment dto
	 */
	@NonNull
	BaseRateDTO convertTo(@NonNull RATE comment);

	/**
	 * Converts to base comment dto list.
	 *
	 * @param comments comment list must not be null
	 * @return a list of base comment dto
	 */
	@NonNull
	List<BaseRateDTO> convertTo(@NonNull List<RATE> comments);

	/**
	 * Converts to base comment dto page.
	 *
	 * @param commentPage comment page must not be null
	 * @return a page of base comment dto
	 */
	@NonNull
	Page<BaseRateDTO> convertTo(@NonNull Page<RATE> commentPage);

	/**
	 * Converts to base comment vo tree.
	 *
	 * @param comments   comments list could be null
	 * @param comparator comment comparator could be null
	 * @return a comment vo tree
	 */
	List<BaseRateVO> convertToVo(@Nullable List<RATE> comments, @Nullable Comparator<BaseRateVO> comparator);

	/**
	 * Target validation.
	 *
	 * @param targetId target id must not be null (product id, sheet id or journal
	 *                 id)
	 */
	void validateTarget(@NonNull Integer targetId);

	/**
	 * Lists a page of top comment.
	 *
	 * @param targetId target id must not be null
	 * @param status   comment status must not be null
	 * @param pageable page info must not be null
	 * @return a page of top comment
	 */
	@NonNull
	Page<RateWithHasChildrenVO> pageTopProductsBy(@NonNull Integer targetId, @NonNull RateStatus status,
			@NonNull Pageable pageable);

	/**
	 * Lists children comments.
	 *
	 * @param targetId        target id must not be null
	 * @param commentParentId comment parent id must not be null
	 * @param status          comment status must not be null
	 * @param sort            sort info must not be null
	 * @return a list of children comment
	 */
	@NonNull
	List<RATE> listChildrenBy(@NonNull Integer targetId, @NonNull Long commentParentId, @NonNull RateStatus status,
			@NonNull Sort sort);

	/**
	 * Lists children comments.
	 *
	 * @param targetId        target id must not be null
	 * @param commentParentId comment parent id must not be null
	 * @param sort            sort info must not be null
	 * @return a list of children comment
	 */
	@NonNull
	List<RATE> listChildrenBy(@NonNull Integer targetId, @NonNull Long commentParentId, @NonNull Sort sort);

	/**
	 * Filters comment ip address.
	 *
	 * @param comment comment dto must not be null
	 */
	@Deprecated
	<T extends BaseRateDTO> T filterIpAddress(@NonNull T comment);

	/**
	 * Filters comment ip address.
	 *
	 * @param comments comment dto list
	 */
	@Deprecated
	<T extends BaseRateDTO> List<T> filterIpAddress(@Nullable List<T> comments);

	/**
	 * Filters comment ip address.
	 *
	 * @param commentPage comment page
	 */
	@Deprecated
	<T extends BaseRateDTO> Page<T> filterIpAddress(@NonNull Page<T> commentPage);

	/**
	 * Replace comment url in batch.
	 *
	 * @param oldUrl old blog url.
	 * @param newUrl new blog url.
	 * @return replaced comments.
	 */
	List<BaseRateDTO> replaceUrl(@NonNull String oldUrl, @NonNull String newUrl);

	Map<Integer, Long> countByProductIds(Collection<Integer> productIds);

	long countByProductId(Integer productId);

}
