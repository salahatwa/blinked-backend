package com.blinked.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.blinked.entities.ProductTag;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.entities.projection.TagProductCountProjection;
import com.blinked.repositories.base.BaseRepository;


/**
 * Product tag repository.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
public interface ProductTagRepository extends BaseRepository<ProductTag, Integer> {

    /**
     * Finds all product tags by product id.
     *
     * @param productId product id must not be null
     * @return a list of product tags
     */
    @NonNull
    List<ProductTag> findAllByProductId(@NonNull Integer productId);

    /**
     * Finds all tag ids by product id.
     *
     * @param productId product id must not be null
     * @return a set of tag id
     */
    @Query("select productTag.tagId from ProductTag productTag where productTag.productId = ?1")
    @NonNull
    Set<Integer> findAllTagIdsByProductId(@NonNull Integer productId);

    /**
     * Finds all product tags by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of product tags
     */
    @NonNull
    List<ProductTag> findAllByTagId(@NonNull Integer tagId);

    /**
     * Finds all product id by tag id.
     *
     * @param tagId tag id must not be null
     * @return a set of product id
     */
    @Query("select productTag.productId from ProductTag productTag where productTag.tagId = ?1")
    @NonNull
    Set<Integer> findAllProductIdsByTagId(@NonNull Integer tagId);

    /**
     * Finds all product id by tag id and product status.
     *
     * @param tagId  tag id must not be null
     * @param status product status
     * @return a set of product id
     */
    @Query("select productTag.productId from ProductTag productTag,Product product where productTag.tagId = ?1 and product.id = productTag.productId and product.status = ?2")
    @NonNull
    Set<Integer> findAllProductIdsByTagId(@NonNull Integer tagId, @NonNull ProductStatus status);

    /**
     * Finds all tags by product id in.
     *
     * @param productIds product id collection
     * @return a list of product tags
     */
    @NonNull
    List<ProductTag> findAllByProductIdIn(@NonNull Collection<Integer> productIds);

    /**
     * Deletes product tags by product id.
     *
     * @param productId product id must not be null
     * @return a list of product tag deleted
     */
    @NonNull
    List<ProductTag> deleteByProductId(@NonNull Integer productId);

    /**
     * Deletes product tags by tag id.
     *
     * @param tagId tag id must not be null
     * @return a list of product tag deleted
     */
    @NonNull
    List<ProductTag> deleteByTagId(@NonNull Integer tagId);

    /**
     * Finds product count by tag id collection.
     *
     * @param tagIds tag id collection must not be null
     * @return a list of tag product count projection
     */
    @Query("select new com.blinked.entities.projection.TagProductCountProjection(count(pt.productId), pt.tagId) from ProductTag pt where pt.tagId in ?1 group by pt.tagId")
    @NonNull
    List<TagProductCountProjection> findProductCountByTagIds(@NonNull Collection<Integer> tagIds);

    /**
     * Finds product count of tag.
     *
     * @return a list of tag product count projection
     */
    @Query("select new com.blinked.entities.projection.TagProductCountProjection(count(pt.productId), pt.tagId) from ProductTag pt group by pt.tagId")
    @NonNull
    List<TagProductCountProjection> findProductCount();
}
