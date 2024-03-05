package com.blinked.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import com.blinked.apis.requests.ProductQuery;
import com.blinked.apis.responses.ArchiveMonthVO;
import com.blinked.apis.responses.ArchiveYearVO;
import com.blinked.apis.responses.ProductDetailVO;
import com.blinked.apis.responses.ProductListVO;
import com.blinked.entities.Product;
import com.blinked.entities.ProductMeta;
import com.blinked.entities.enums.ProductStatus;

import jakarta.validation.constraints.NotNull;

/**
 * Product service interface.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
public interface ProductService extends BaseProductService<Product> {

	/**
	 * Pages products.
	 *
	 * @param productQuery product query must not be null
	 * @param pageable     page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<Product> pageBy(@NonNull ProductQuery productQuery, @NonNull Pageable pageable);

	/**
	 * Pages product by keyword
	 *
	 * @param keyword  keyword
	 * @param pageable pageable
	 * @return a page of product
	 */
	@NonNull
	Page<Product> pageBy(@NonNull String keyword, @NonNull Pageable pageable);

	@NonNull
	List<ProductListVO> topProducts(Pageable pageable);

	/**
	 * Creates product by product param.
	 *
	 * @param product     product must not be null
	 * @param tagIds      tag id set
	 * @param categoryIds category id set
	 * @param metas       metas
	 * @param autoSave    autoSave
	 * @return product created
	 */
	@NonNull
	ProductDetailVO createBy(@NonNull Product product, Set<String> tagIds, Set<String> categoryIds,
			Set<ProductMeta> metas, boolean autoSave);

	/**
	 * Creates product by product param.
	 *
	 * @param product     product must not be null
	 * @param tagIds      tag id set
	 * @param categoryIds category id set
	 * @param autoSave    autoSave
	 * @return product created
	 */
	@NonNull
	ProductDetailVO createBy(@NonNull Product product, Set<String> tagIds, Set<String> categoryIds, boolean autoSave);

	/**
	 * Updates product by product, tag id set and category id set.
	 *
	 * @param productToUpdate product to update must not be null
	 * @param tagIds          tag id set
	 * @param categoryIds     category id set
	 * @param metas           metas
	 * @param autoSave        autoSave
	 * @return updated product
	 */
	@NonNull
	ProductDetailVO updateBy(@NonNull Product productToUpdate, Set<String> tagIds, Set<String> categoryIds,
			Set<ProductMeta> metas, boolean autoSave);

	/**
	 * Gets product by product status and slug.
	 *
	 * @param status product status must not be null
	 * @param slug   product slug must not be blank
	 * @return product info
	 */
	@NonNull
	Product getBy(@NonNull ProductStatus status, @NonNull String slug);

	/**
	 * Gets product by product year and month and slug.
	 *
	 * @param year  product create year.
	 * @param month product create month.
	 * @param slug  product slug.
	 * @return product info
	 */
	@NonNull
	Product getBy(@NonNull Integer year, @NonNull Integer month, @NonNull String slug);

	/**
	 * Gets product by product year and slug.
	 *
	 * @param year product create year.
	 * @param slug product slug.
	 * @return product info
	 */
	@NonNull
	Product getBy(@NonNull Integer year, @NonNull String slug);

	/**
	 * Gets product by product year and month and slug.
	 *
	 * @param year   product create year.
	 * @param month  product create month.
	 * @param slug   product slug.
	 * @param status product status.
	 * @return product info
	 */
	@NonNull
	Product getBy(@NonNull Integer year, @NonNull Integer month, @NonNull String slug, @NonNull ProductStatus status);

	/**
	 * Gets product by product year and month and slug.
	 *
	 * @param year  product create year.
	 * @param month product create month.
	 * @param day   product create day.
	 * @param slug  product slug.
	 * @return product info
	 */
	@NonNull
	Product getBy(@NonNull Integer year, @NonNull Integer month, @NonNull Integer day, @NonNull String slug);

	/**
	 * Gets product by product year and month and slug.
	 *
	 * @param year   product create year.
	 * @param month  product create month.
	 * @param day    product create day.
	 * @param slug   product slug.
	 * @param status product status.
	 * @return product info
	 */
	@NonNull
	Product getBy(@NonNull Integer year, @NonNull Integer month, @NonNull Integer day, @NonNull String slug,
			@NonNull ProductStatus status);

	/**
	 * Removes products in batch.
	 *
	 * @param ids ids must not be null.
	 * @return a list of deleted product.
	 */
	@NonNull
	List<Product> removeByIds(@NonNull Collection<Integer> ids);

	/**
	 * Lists year archives.
	 *
	 * @return a list of year archive
	 */
	@NonNull
	List<ArchiveYearVO> listYearArchives();

	/**
	 * Lists month archives.
	 *
	 * @return a list of month archive
	 */
	@NonNull
	List<ArchiveMonthVO> listMonthArchives();

	/**
	 * Convert to year archives
	 *
	 * @param products products must not be null
	 * @return list of ArchiveYearVO
	 */
	List<ArchiveYearVO> convertToYearArchives(@NonNull List<Product> products);

	/**
	 * Convert to month archives
	 *
	 * @param products products must not be null
	 * @return list of ArchiveMonthVO
	 */
	List<ArchiveMonthVO> convertToMonthArchives(@NonNull List<Product> products);

	/**
	 * Export product to markdown file by product id.
	 *
	 * @param id product id
	 * @return markdown file content
	 */
	@NonNull
	String exportMarkdown(@NonNull Integer id);

	/**
	 * Export product to markdown file by product.
	 *
	 * @param product current product
	 * @return markdown file content
	 */
	@NonNull
	String exportMarkdown(@NonNull Product product);

	/**
	 * Converts to detail vo.
	 *
	 * @param product product must not be null
	 * @return product detail vo
	 */
	@NonNull
	ProductDetailVO convertToDetailVo(@NonNull Product product);

	/**
	 * Converts to a page of product list vo.
	 *
	 * @param productPage product page must not be null
	 * @return a page of product list vo
	 */
	@NonNull
	Page<ProductListVO> convertToListVo(@NonNull Page<Product> productPage);

	/**
	 * Converts to a list of product list vo.
	 *
	 * @param products product must not be null
	 * @return a list of product list vo
	 */
	@NonNull
	List<ProductListVO> convertToListVo(@NonNull List<Product> products);

	/**
	 * Converts to a page of detail vo.
	 *
	 * @param productPage product page must not be null
	 * @return a page of product detail vo
	 */
	Page<ProductDetailVO> convertToDetailVo(@NonNull Page<Product> productPage);

	void publishVisitEvent(Integer productId);

	/**
	 * Get Product Pageable default sort
	 *
	 * @return
	 * @Desc contains three parts. First, Top Priority; Second, From Custom index
	 *       sort; Third, basic id sort
	 */
	@NotNull
	Sort getProductDefaultSort();

	List<Product> topView();

	Object updateSequence(String table);
}
