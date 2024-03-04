package com.blinked.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.api.common.repo.BaseRepository;
import com.blinked.entities.BaseProduct;
import com.blinked.entities.enums.ProductStatus;

import jakarta.transaction.Transactional;

/**
 *
 * @author ssatwa
 * @date 2019-03-22
 */
public interface BaseProductRepository<PRODUCT extends BaseProduct> extends BaseRepository<PRODUCT, Integer> {

	/**
	 * Counts visits. (Need to be overridden)
	 *
	 * @return total visits
	 */
	@Query("select sum(p.visits) from BaseProduct p")
	Long countVisit();

	/**
	 * Counts likes. (Need to be overridden)
	 *
	 * @return total likes
	 */
	@Query("select sum(p.likes) from BaseProduct p")
	Long countLike();

	/**
	 * Finds products by status and pageable.
	 *
	 * @param status   product status must not be null
	 * @param pageable page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatus(@NonNull ProductStatus status, @NonNull Pageable pageable);

	/**
	 * Finds products by status.
	 *
	 * @param status product staus must not be null
	 * @return a list of product
	 */
	@NonNull
	List<PRODUCT> findAllByStatus(@NonNull ProductStatus status);

	/**
	 * Finds products by status.
	 *
	 * @param status product staus must not be null
	 * @param sort   sort info must not be null
	 * @return a list of product
	 */
	@NonNull
	List<PRODUCT> findAllByStatus(@NonNull ProductStatus status, @NonNull Sort sort);

	/**
	 * Finds all product by status and create time before.
	 *
	 * @param status     status must not be null
	 * @param createTime create time must not be null
	 * @param pageable   page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatusAndCreateTimeBefore(@NonNull ProductStatus status, @NonNull Date createTime,
			@NonNull Pageable pageable);

	/**
	 * Finds all product by status and create time after.
	 *
	 * @param status     status must not be null
	 * @param createTime create time must not be null
	 * @param pageable   page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatusAndCreateTimeAfter(@NonNull ProductStatus status, @NonNull Date createTime,
			@NonNull Pageable pageable);

	/**
	 * Finds all product by status and edit time before.
	 *
	 * @param status   status must not be null
	 * @param editTime edit time must not be null
	 * @param pageable page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatusAndEditTimeBefore(@NonNull ProductStatus status, @NonNull Date editTime,
			@NonNull Pageable pageable);

	/**
	 * Finds all product by status and edit time after.
	 *
	 * @param status   status must not be null
	 * @param editTime edit time must not be null
	 * @param pageable page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatusAndEditTimeAfter(@NonNull ProductStatus status, @NonNull Date editTime,
			@NonNull Pageable pageable);

	/**
	 * Finds all product by status and visits before.
	 *
	 * @param status   status must not be null
	 * @param visits   visits must not be null
	 * @param pageable page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatusAndVisitsBefore(@NonNull ProductStatus status, @NonNull Long visits,
			@NonNull Pageable pageable);

	/**
	 * Finds all product by status and visits after.
	 *
	 * @param status   status must not be null
	 * @param visits   visits must not be null
	 * @param pageable page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> findAllByStatusAndVisitsAfter(@NonNull ProductStatus status, @NonNull Long visits,
			@NonNull Pageable pageable);

	@NonNull
	Page<PRODUCT> findAllByStatusAndTopPriority(@NonNull ProductStatus status, @NonNull Integer priority,
			@NonNull Pageable pageable);

	@NonNull
	List<PRODUCT> findAllByStatusAndTopPriority(@NonNull ProductStatus status, @NonNull Integer priority);

	/**
	 * Gets product by slug and status.
	 *
	 * @param slug   slug must not be blank
	 * @param status status must not be null
	 * @return an optional product
	 */
	@NonNull
	Optional<PRODUCT> getBySlugAndStatus(@NonNull String slug, @NonNull ProductStatus status);

	/**
	 * Gets product by id and status.
	 *
	 * @param id     id must not be blank
	 * @param status status must not be null
	 * @return an optional product
	 */
	@NonNull
	Optional<PRODUCT> getByIdAndStatus(@NonNull Integer id, @NonNull ProductStatus status);

	/**
	 * Counts products by status and type.
	 *
	 * @param status status
	 * @return products count
	 */
	long countByStatus(@NonNull ProductStatus status);

	/**
	 * Determine if the slug exists.
	 *
	 * @param slug slug must not be null.
	 * @return true or false.
	 */
	boolean existsBySlug(@NonNull String slug);

	/**
	 * Determine if the slug exists.
	 *
	 * @param id   product id must not be null.
	 * @param slug slug must not be null.
	 * @return true or false.
	 */
	boolean existsByIdNotAndSlug(@NonNull Integer id, @NonNull String slug);

	/**
	 * Get product by slug
	 *
	 * @param slug product slug
	 * @return Optional<Product>
	 */
	@Transactional
	Optional<PRODUCT> getBySlug(@NonNull String slug);

	/**
	 * Updates product visits.
	 *
	 * @param visits    visit delta
	 * @param productId product id must not be null
	 * @return updated rows
	 */
	@Modifying
	@Query("update BaseProduct p set p.visits = p.visits + :visits where p.id = :productId")
	int updateVisit(@Param("visits") long visits, @Param("productId") @NonNull Integer productId);

	/**
	 * Updates product likes.
	 *
	 * @param likes     likes delta
	 * @param productId product id must not be null
	 * @return updated rows
	 */
	@Modifying
	@Query("update BaseProduct p set p.likes = p.likes + :likes where p.id = :productId")
	int updateLikes(@Param("likes") long likes, @Param("productId") @NonNull Integer productId);

	/**
	 * Updates product original content.
	 *
	 * @param content   content could be blank but disallow to be null
	 * @param productId product id must not be null
	 * @return updated rows
	 */
	@Modifying
	@Query("update BaseProduct p set p.template = :template where p.id = :productId")
	int updateTemplate(@Param("template") @NonNull String template, @Param("productId") @NonNull Integer productId);

	/**
	 * Updates product status by product id.
	 *
	 * @param status    product status must not be null.
	 * @param productId product id must not be null.
	 * @return updated rows.
	 */
	@Modifying
	@Query("update BaseProduct p set p.status = :status where p.id = :productId")
	int updateStatus(@Param("status") @NonNull ProductStatus status, @Param("productId") @NonNull Integer productId);

}
