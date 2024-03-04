package com.blinked.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.api.common.repo.BaseRepository;
import com.blinked.entities.ProductCategory;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.entities.projection.CategoryProductCountProjection;

/**
 *
 * @author ssatwa
 * @date 2019-03-19
 */
public interface ProductCategoryRepository extends BaseRepository<ProductCategory, Integer> {

	/**
	 * Finds all category ids by post id
	 *
	 * @param postId post id must not be null
	 * @return a list of category id
	 */
	@NonNull
	@Query("select productCategory.categoryId from ProductCategory productCategory where productCategory.productId = ?1")
	Set<String> findAllCategoryIdsByProductId(@NonNull Integer productId);

	/**
	 * Finds all post ids by category id.
	 *
	 * @param categoryId category id must not be null
	 * @return a set of post id
	 */
	@NonNull
	@Query("select productCategory.productId from ProductCategory productCategory where productCategory.categoryId = ?1")
	Set<Integer> findAllProductIdsByCategoryId(@NonNull String categoryId);

	/**
	 * Finds all product ids by category id and post status.
	 *
	 * @param categoryId category id must not be null
	 * @param status     product status must not be null
	 * @return a set of post id
	 */
	@NonNull
	@Query("select productCategory.productId from ProductCategory productCategory, Product product where productCategory.categoryId = ?1 and product.id = productCategory.productId and product.status = ?2")
	Set<Integer> findAllProductIdsByCategoryId(@NonNull String categoryId, @NonNull ProductStatus status);

	/**
	 * Finds all product categories by post id in.
	 *
	 * @param productIds product id collection must not be null
	 * @return a list of post category
	 */
	@NonNull
	List<ProductCategory> findAllByProductIdIn(@NonNull Collection<Integer> productIds);

	/**
	 * Finds all post categories by post id.
	 *
	 * @param postId post id must not be null
	 * @return a list of post category
	 */
	List<ProductCategory> findAllByProductId(int productId);

	@Query(nativeQuery = true, value = "select * from product_categories where product_id= ?1")
	List<ProductCategory> findAllByProductIdNative(Integer productId);

	@NonNull
	@Query("select productCategory from ProductCategory productCategory where productCategory.productId = ?1")
	List<ProductCategory> findAllByProductIdJPA(@NonNull Integer productId);

	/**
	 * Finds all post categories by category id.
	 *
	 * @param categoryId category id must not be null
	 * @return a list of post category
	 */
	@NonNull
	List<ProductCategory> findAllByCategoryId(@NonNull String categoryId);

	/**
	 * Deletes post categories by post id.
	 *
	 * @param postId post id must not be null
	 * @return a list of post category deleted
	 */
	@NonNull
	List<ProductCategory> deleteByProductId(@NonNull Integer productId);

	/**
	 * Deletes post categories by category id.
	 *
	 * @param categoryId category id must not be null
	 * @return a list of post category deleted
	 */
	@NonNull
	List<ProductCategory> deleteByCategoryId(@NonNull String categoryId);

	@Query("select new com.blinked.entities.projection.CategoryProductCountProjection(count(pc.productId), pc.categoryId) from ProductCategory pc group by pc.categoryId")
	@NonNull
	List<CategoryProductCountProjection> findProductCount();
}
