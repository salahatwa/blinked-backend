package com.blinked.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.api.common.repo.CrudService;
import com.blinked.entities.Product;
import com.blinked.entities.ProductTag;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.TagWithProductCountDTO;
import com.blinked.entities.enums.ProductStatus;

/**
 * Product tag service interface.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
public interface ProductTagService extends CrudService<ProductTag, Integer> {

	/**
	 * Lists tags by productId.
	 *
	 * @param productId productId must not be null
	 * @return a list of tag
	 */
	@NonNull
	List<Tag> listTagsBy(@NonNull Integer productId);

	/**
	 * List tag with post count output dtos.
	 *
	 * @param sort sort info
	 * @return a list of tag with post count output dto
	 */
	@NonNull
	List<TagWithProductCountDTO> listTagWithCountDtos(@NonNull Sort sort);

	/**
	 * Lists tags list map by productId.
	 *
	 * @param productIds productId collection
	 * @return tag map (key: productId, value: a list of tags)
	 */
	@NonNull
	Map<Integer, List<Tag>> listTagListMapBy(@Nullable Collection<Integer> productIds);

	/**
	 * Lists posts by tag id.
	 *
	 * @param tagId tag id must not be null
	 * @return a list of post
	 */
	@NonNull
	List<Product> listProductsBy(@NonNull String tagId);

	/**
	 * Lists posts by tag id and post status.
	 *
	 * @param tagId  tag id must not be null
	 * @param status post status
	 * @return a list of post
	 */
	@NonNull
	List<Product> listProductsTagBy(@NonNull String tagId, @NonNull ProductStatus status);

	/**
	 * Lists posts by tag slug and post status.
	 *
	 * @param slug   tag slug must not be null
	 * @param status post status
	 * @return a list of post
	 */
	@NonNull
	List<Product> listProductsBy(@NonNull String slug, @NonNull ProductStatus status);

	/**
	 * Pages posts by tag id.
	 *
	 * @param tagId    must not be null
	 * @param pageable must not be null
	 * @return a page of post
	 */
	Page<Product> pageProductsBy(@NonNull String tagId, Pageable pageable);

	/**
	 * Pages posts by tag id and post status.
	 *
	 * @param tagId    must not be null
	 * @param status   post status
	 * @param pageable must not be null
	 * @return a page of post
	 */
	Page<Product> pageProductsBy(@NonNull String tagId, @NonNull ProductStatus status, Pageable pageable);

	/**
	 * Merges or creates post tags by productId and tag id set if absent.
	 *
	 * @param productId productId must not be null
	 * @param tagIds    tag id set
	 * @return a list of post tag
	 */
	@NonNull
	List<ProductTag> mergeOrCreateByIfAbsent(@NonNull Integer productId, @Nullable Set<String> tagIds);

	/**
	 * Lists post tags by productId.
	 *
	 * @param productId productId must not be null
	 * @return a list of post tag
	 */
	@NonNull
	List<ProductTag> listByProductId(@NonNull Integer productId);

	/**
	 * Lists post tags by tag id.
	 *
	 * @param tagId tag id must not be null
	 * @return a list of post tag
	 */
	@NonNull
	List<ProductTag> listByTagId(@NonNull String tagId);

	/**
	 * Lists tag id set by productId.
	 *
	 * @param productId productId must not be null
	 * @return a set of tag id
	 */
	@NonNull
	Set<String> listTagIdsByProductId(@NonNull Integer productId);

	/**
	 * Removes post tags by productId.
	 *
	 * @param productId productId must not be null
	 * @return a list of post tag
	 */
	@NonNull
	@Transactional
	List<ProductTag> removeByProductId(@NonNull Integer productId);

	/**
	 * Removes post tags by tag id.
	 *
	 * @param tagId tag id must not be null
	 * @return a list of post tag
	 */
	@NonNull
	@Transactional
	List<ProductTag> removeByTagId(@NonNull String tagId);
}
