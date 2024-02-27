package com.blinked.services.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.blinked.entities.BaseProduct;
import com.blinked.entities.dto.BaseProductDetailDTO;
import com.blinked.entities.dto.BaseProductMinimalDTO;
import com.blinked.entities.dto.BaseProductSimpleDTO;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.repositories.base.CrudService;

/**
 * Base product service implementation.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
public interface BaseProductService<PRODUCT extends BaseProduct> extends CrudService<PRODUCT, Integer> {

	/**
	 * Counts visit total number.
	 *
	 * @return visit total number
	 */
	long countVisit();

	/**
	 * Counts like total number.
	 *
	 * @return like total number
	 */
	long countLike();

	/**
	 * Count products by status.
	 *
	 * @param status status
	 * @return products count
	 */
	long countByStatus(ProductStatus status);

	/**
	 * Get product by slug.
	 *
	 * @param slug product slug.
	 * @return Product
	 */
	@NonNull
	PRODUCT getBySlug(@NonNull String slug);

	/**
	 * Gets product by product status and slug.
	 *
	 * @param status product status must not be null
	 * @param slug   product slug must not be blank
	 * @return product info
	 */
	@NonNull
	PRODUCT getBy(@NonNull ProductStatus status, @NonNull String slug);

	/**
	 * Gets product by product status and id.
	 *
	 * @param status product status must not be null
	 * @param id     product id must not be blank
	 * @return product info
	 */
	@NonNull
	PRODUCT getBy(@NonNull ProductStatus status, @NonNull Integer id);

	/**
	 * Lists all products by product status.
	 *
	 * @param status product status must not be null
	 * @return a list of product
	 */
	@NonNull
	List<PRODUCT> listAllBy(@NonNull ProductStatus status);

	/**
	 * Lists previous products.
	 *
	 * @param product product must not be null
	 * @param size    previous max product size
	 * @return a list of previous product
	 */
	@NonNull
	List<PRODUCT> listPrevProducts(@NonNull PRODUCT product, int size);

	/**
	 * Lits next products.
	 *
	 * @param product product must not be null
	 * @param size    next max product size
	 * @return a list of next product
	 */
	@NonNull
	List<PRODUCT> listNextProducts(@NonNull PRODUCT product, int size);

	/**
	 * Gets previous product.
	 *
	 * @param product product must not be null
	 * @return an optional product
	 */
	@NonNull
	Optional<PRODUCT> getPrevProduct(@NonNull PRODUCT product);

	/**
	 * Gets next product.
	 *
	 * @param product product must not be null
	 * @return an optional product
	 */
	@NonNull
	Optional<PRODUCT> getNextProduct(@NonNull PRODUCT product);

	/**
	 * Pages latest products.
	 *
	 * @param top top number must not be less than 0
	 * @return latest products
	 */
	@NonNull
	Page<PRODUCT> pageLatest(int top);

	/**
	 * Lists latest products.
	 *
	 * @param top top number must not be less than 0
	 * @return latest products
	 */
	@NonNull
	List<PRODUCT> listLatest(int top);

	/**
	 * Gets a page of sheet.
	 *
	 * @param pageable page info must not be null
	 * @return a page of sheet
	 */
	@NonNull
	Page<PRODUCT> pageBy(@NonNull Pageable pageable);

	/**
	 * Lists by status.
	 *
	 * @param status   product status must not be null
	 * @param pageable page info must not be null
	 * @return a page of product
	 */
	@NonNull
	Page<PRODUCT> pageBy(@NonNull ProductStatus status, @NonNull Pageable pageable);

	/**
	 * Increases product visits.
	 *
	 * @param visits    visits must not be less than 1
	 * @param productId product id must not be null
	 */
	void increaseVisit(long visits, @NonNull Integer productId);

	/**
	 * Increase product likes.
	 *
	 * @param likes     likes must not be less than 1
	 * @param productId product id must not be null
	 */
	void increaseLike(long likes, @NonNull Integer productId);

	/**
	 * Increases product visits (1).
	 *
	 * @param productId product id must not be null
	 */
	void increaseVisit(@NonNull Integer productId);

	/**
	 * Increases product likes(1).
	 *
	 * @param productId product id must not be null
	 */
	void increaseLike(@NonNull Integer productId);

	/**
	 * Creates or updates by product.
	 *
	 * @param product product must not be null
	 * @return created or updated product
	 */
	@NonNull
	PRODUCT createOrUpdateBy(@NonNull PRODUCT product);

	/**
	 * Filters product content if the password is not blank.
	 *
	 * @param product original product must not be null
	 * @return filtered product
	 */
	@NonNull
	PRODUCT filterIfEncrypt(@NonNull PRODUCT product);

	/**
	 * Convert POST to minimal dto.
	 *
	 * @param product product must not be null.
	 * @return minimal dto.
	 */
	@NonNull
	BaseProductMinimalDTO convertToMinimal(@NonNull PRODUCT product);

	/**
	 * Convert list of POST to minimal dto of list.
	 *
	 * @param products products must not be null.
	 * @return a list of minimal dto.
	 */
	@NonNull
	List<BaseProductMinimalDTO> convertToMinimal(@Nullable List<PRODUCT> products);

	/**
	 * Convert page of POST to minimal dto of page.
	 *
	 * @param productPage productPage must not be null.
	 * @return a page of minimal dto.
	 */
	@NonNull
	Page<BaseProductMinimalDTO> convertToMinimal(@NonNull Page<PRODUCT> productPage);

	/**
	 * Convert POST to simple dto.
	 *
	 * @param product product must not be null.
	 * @return simple dto.
	 */
	@NonNull
	BaseProductSimpleDTO convertToSimple(@NonNull PRODUCT product);

	/**
	 * Convert list of POST to list of simple dto.
	 *
	 * @param products products must not be null.
	 * @return a list of simple dto.
	 */
	@NonNull
	List<BaseProductSimpleDTO> convertToSimple(@Nullable List<PRODUCT> products);

	/**
	 * Convert page of POST to page of simple dto.
	 *
	 * @param productPage productPage must not be null.
	 * @return a page of simple dto.
	 */
	@NonNull
	Page<BaseProductSimpleDTO> convertToSimple(@NonNull Page<PRODUCT> productPage);

	/**
	 * Convert POST to detail dto.
	 *
	 * @param product product must not be null.
	 * @return detail dto.
	 */
	@NonNull
	BaseProductDetailDTO convertToDetail(@NonNull PRODUCT product);

	/**
	 * Updates draft content.
	 *
	 * @param content   draft content could be blank
	 * @param productId product id must not be null
	 * @return updated product
	 */
	@NonNull
	PRODUCT updateTemplate(@Nullable String content, @NonNull Integer productId);

	/**
	 * Updates product status.
	 *
	 * @param status    product status must not be null
	 * @param productId product id must not be null
	 * @return updated product
	 */
	@NonNull
	PRODUCT updateStatus(@NonNull ProductStatus status, @NonNull Integer productId);

	/**
	 * Updates product status by ids.
	 *
	 * @param ids    product ids must not be null
	 * @param status product status must not be null
	 * @return updated products
	 */
	@NonNull
	List<PRODUCT> updateStatusByIds(@NonNull List<Integer> ids, @NonNull ProductStatus status);

	
	/**
	 * Generate description.
	 *
	 * @param content html content must not be null.
	 * @return description
	 */
	String generateDescription(@NonNull String content);
}
