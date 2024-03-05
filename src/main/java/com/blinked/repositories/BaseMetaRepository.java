package com.blinked.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import com.api.common.repo.BaseRepository;
import com.blinked.entities.BaseMeta;

/**
 * Base meta repository.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@NoRepositoryBean
public interface BaseMetaRepository<META extends BaseMeta>
		extends BaseRepository<META, String>, JpaSpecificationExecutor<META> {

	/**
	 * Finds all metas by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of meta
	 */
	@NonNull
	List<META> findAllByProductId(@NonNull Integer productId);

	/**
	 * Deletes product metas by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of product meta deleted
	 */
	@NonNull
	List<META> deleteByProductId(@NonNull Integer productId);

	/**
	 * Finds all product metas by product id.
	 *
	 * @param productIds product id must not be null
	 * @return a list of product meta
	 */
	@NonNull
	List<META> findAllByProductIdIn(@NonNull Set<Integer> productIds);
}
