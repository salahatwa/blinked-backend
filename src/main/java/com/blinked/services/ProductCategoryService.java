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
import com.blinked.entities.Category;
import com.blinked.entities.Product;
import com.blinked.entities.ProductCategory;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.entities.projection.CategoryWithProductCountProjection;

/**
 *
 * @author ssatwa
 * @date 2019-03-19
 */
public interface ProductCategoryService extends CrudService<ProductCategory, Integer> {

	/**
	 * Lists category by product id.
	 *
	 * @param productId must not be null
	 * @return a list of category
	 */
	@NonNull
	List<Category> listCategoriesBy(@NonNull Integer productId);

	/**
	 * List category list map by product id collection.
	 *
	 * @param productIds product id collection
	 * @return a category list map (key: productId, value: a list of category)
	 */
	@NonNull
	Map<Integer, List<Category>> listCategoryListMap(@Nullable Collection<Integer> productIds);

	/**
	 * Lists product by category id.
	 *
	 * @param categoryId category id must not be null
	 * @return a list of products
	 */
	@NonNull
	List<Product> listProductBy(@NonNull String categoryId);

	/**
	 * Lists product by category id and product status.
	 *
	 * @param categoryId category id must not be null
	 * @param status     product status
	 * @return a list of product
	 */
	@NonNull
	List<Product> listProductByCat(@NonNull String categoryId, @NonNull ProductStatus status);

	/**
	 * Lists product by category slug and product status.
	 *
	 * @param slug   category slug must not be null
	 * @param status product status
	 * @return a list of products
	 */
	@NonNull
	List<Product> listProductBy(@NonNull String slug, @NonNull ProductStatus status);

	/**
	 * Pages product by category id.
	 *
	 * @param categoryId category id must not be null
	 * @param pageable   pageable
	 * @return page of product
	 */
	@NonNull
	Page<Product> pageProductBy(@NonNull String categoryId, Pageable pageable);

	/**
	 * Pages product by category id and product status.
	 *
	 * @param categoryId category id must not be null
	 * @param status product status
	 * @param pageable   pageable
	 * @return page of product
	 */
	@NonNull
	Page<Product> pageProductBy(@NonNull String categoryId, @NonNull ProductStatus status, Pageable pageable);

	/**
	 * Merges or creates products categories by product id and category id set if absent.
	 *
	 * @param productId    product id must not be null
	 * @param categoryIds category id set
	 * @return a list of product category
	 */
	@NonNull
	List<ProductCategory> mergeOrCreateByIfAbsent(@NonNull Integer productId, @Nullable Set<String> categoryIds);

	/**
	 * Lists by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of product category
	 */
	@NonNull
	List<ProductCategory> listByProductId(@NonNull Integer productId);

	/**
	 * Lists by category id.
	 *
	 * @param categoryId category id must not be null
	 * @return a list of product category
	 */
	@NonNull
	List<ProductCategory> listByCategoryId(@NonNull String categoryId);

	/**
	 * List category id set by product id.
	 *
	 * @param productId product id must not be null
	 * @return a set of category id
	 */
	@NonNull
	Set<String> listCategoryIdsByProductId(@NonNull Integer productId);

	/**
	 * Removes product categories by product id.
	 *
	 * @param productId product id must not be null
	 * @return a list of product category deleted
	 */
	@NonNull
	@Transactional
	List<ProductCategory> removeByProductId(@NonNull Integer productId);

	/**
	 * Removes product categories by category id.
	 *
	 * @param categoryId category id must not be null
	 * @return a list of product category deleted
	 */
	@NonNull
	@Transactional
	List<ProductCategory> removeByCategoryId(@NonNull String categoryId);

	/**
	 * Lists category with product count.
	 *
	 * @param sort sort info
	 * @return a list of category dto
	 */
	@NonNull
	List<CategoryWithProductCountProjection> listCategoryWithProductCountDto(@NonNull Sort sort);

	/**
	 * product_categories
	 * 
	 * @param table
	 * @return
	 */
	Object updateSequence(String table);
}
