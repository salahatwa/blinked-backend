package com.blinked.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.blinked.entities.ProductRate;
import com.blinked.entities.projection.RateChildrenCountProjection;
import com.blinked.entities.projection.RateCountProjection;
import com.blinked.repositories.base.BaseRateRepository;

/**
 * ProductRate repository.
 *
 * @author ssatwa
 * @date 2019-03-21
 */
public interface ProductRateRepository extends BaseRateRepository<ProductRate> {

	/**
	 * Count rates by product ids.
	 *
	 * @param productIds product id collection must not be null
	 * @return a list of CommentCountProjection
	 */
	@Query("select new com.blinked.entities.projection.RateCountProjection(count(rate.id), rate.productId) "
			+ "from ProductRate rate " + "where rate.productId in ?1 group by rate.productId")
	@NonNull
	@Override
	List<RateCountProjection> countByProductIds(@NonNull Collection<Integer> productIds);

	/**
	 * Finds direct children count by rate ids.
	 *
	 * @param rateIds rate ids must not be null.
	 * @return a list of CommentChildrenCountProjection
	 */
	@Query("select new com.blinked.entities.projection.RateChildrenCountProjection(count(rate.id), rate.parentId) "
			+ "from ProductRate rate " + "where rate.parentId in ?1 " + "group by rate.parentId")
	@NonNull
	@Override
	List<RateChildrenCountProjection> findDirectChildrenCount(@NonNull Collection<Long> rateIds);

	
	@Query("SELECT COUNT(id) FROM ProductRate WHERE ipAddress=?1 AND updateTime BETWEEN ?2 AND ?3 AND status <> 2")
	int countByIpAndTime(String ipAddress, Date startTime, Date endTime);
}
