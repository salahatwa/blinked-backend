package com.blinked.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.api.common.annotations.SensitiveConceal;
import com.api.common.repo.BaseRepository;
import com.blinked.entities.BaseRate;
import com.blinked.entities.enums.RateStatus;
import com.blinked.entities.projection.RateChildrenCountProjection;
import com.blinked.entities.projection.RateCountProjection;

/**
 * Base rate repository.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
@NoRepositoryBean
public interface BaseRateRepository<RATE extends BaseRate>
		extends BaseRepository<RATE, Long>, JpaSpecificationExecutor<RATE> {

	/**
	 * Finds all rates by status.
	 *
	 * @param status   status must not be null
	 * @param pageable page info must not be null
	 * @return a page of rate
	 */
	@NonNull
	@SensitiveConceal
	Page<RATE> findAllByStatus(@Nullable RateStatus status, @NonNull Pageable pageable);

	/**
	 * Finds all rates by product ids.
	 *
	 * @param productIds product ids must not be null
	 * @return a list of rate
	 */
	@NonNull
	@SensitiveConceal
	List<RATE> findAllByProductIdIn(@NonNull Collection<Integer> productIds);

	/**
	 * Finds all rates by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of rate
	 */
	@NonNull
	@SensitiveConceal
	List<RATE> findAllByProductId(@NonNull Integer productId);

	/**
	 * Counts rate count by product id collection.
	 *
	 * @param productIds product id collection must not be null
	 * @return a list of rate count
	 */
	@Query("select new com.blinked.entities.projection.RateCountProjection(count(rate.id), rate.productId) "
			+ "from BaseRate rate " + "where rate.productId in ?1 " + "group by rate.productId")
	@NonNull
	List<RateCountProjection> countByProductIds(@NonNull Collection<Integer> productIds);

	/**
	 * Count rates by product id.
	 *
	 * @param productId product id must not be null.
	 * @return rates count
	 */
	long countByProductId(@NonNull Integer productId);

	/**
	 * Counts by rate status.
	 *
	 * @param status rate status must not be null
	 * @return rate count
	 */
	long countByStatus(@NonNull RateStatus status);

	/**
	 * Removes rates by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of rate deleted
	 */
	@NonNull
	List<RATE> deleteByProductId(@NonNull Integer productId);

	/**
	 * Removes rates by parent id.
	 *
	 * @param id rate id must not be null
	 * @return a list of rate deleted
	 */
	@NonNull
	List<RATE> deleteByParentId(@NonNull Long id);

	/**
	 * Finds rates by product id, rate status.
	 *
	 * @param productId product id must not be null
	 * @param status    rate status must not be null
	 * @return a list of rate
	 */
	@NonNull
	@SensitiveConceal
	List<RATE> findAllByProductIdAndStatus(Integer productId, RateStatus status);

	/**
	 * Finds rates by product id and rate status.
	 *
	 * @param productId product id must not be null
	 * @param status    rate status must not be null
	 * @param pageable  page info must not be null
	 * @return a page of rate
	 */
	@NonNull
	@SensitiveConceal
	Page<RATE> findAllByProductIdAndStatus(Integer productId, RateStatus status, Pageable pageable);

	/**
	 * Finds rates by product id, rate status and parent id.
	 *
	 * @param productId product id must not be null
	 * @param status    rate status must not be null
	 * @param parentId  rate parent id must not be null
	 * @return a list of rate
	 */
	@NonNull
	@SensitiveConceal
	List<RATE> findAllByProductIdAndStatusAndParentId(@NonNull Integer productId, @NonNull RateStatus status,
			@NonNull Long parentId);

	/**
	 * Finds rates by product id and parent id.
	 *
	 * @param productId product id must not be null
	 * @param parentId  rate parent id must not be null
	 * @return a list of rate
	 */
	@NonNull
	@SensitiveConceal
	List<RATE> findAllByProductIdAndParentId(@NonNull Integer productId, @NonNull Long parentId);

	/**
	 * Finds all rates by status and parent id collection.
	 *
	 * @param status    rate status must not be null
	 * @param parentIds parent id collection must not be null
	 * @return a list of rate
	 */
	@NonNull
	@SensitiveConceal
	List<RATE> findAllByStatusAndParentIdIn(@NonNull RateStatus status, @NonNull Collection<Long> parentIds);

	/**
	 * Finds all rates by parent id collection.
	 *
	 * @param parentIds parent id collection must not be null
	 * @return a list of rate
	 */
	@SensitiveConceal
	List<RATE> findAllByParentIdIn(@NonNull Collection<Long> parentIds);

	/**
	 * Finds rates by product id, rate status and parent id.
	 *
	 * @param productId product id must not be null
	 * @param status    rate status must not be null
	 * @param parentId  rate parent id must not be null
	 * @param pageable  page info must not be null
	 * @return a page of rate
	 */
	@NonNull
	@SensitiveConceal
	Page<RATE> findAllByProductIdAndStatusAndParentId(Integer productId, RateStatus status, Long parentId,
			Pageable pageable);

	/**
	 * Finds direct children count by rate ids.
	 *
	 * @param rateIds rate ids must not be null.
	 * @return a list of CommentChildrenCountProjection
	 */
	@Query("select new com.blinked.entities.projection.RateChildrenCountProjection(count(rate.id), rate.parentId) "
			+ "from BaseRate rate " + "where rate.parentId in ?1 " + "group by rate.parentId")
	@NonNull
	List<RateChildrenCountProjection> findDirectChildrenCount(@NonNull Collection<Long> rateIds);
}
