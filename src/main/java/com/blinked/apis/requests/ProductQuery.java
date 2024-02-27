package com.blinked.apis.requests;

import com.blinked.entities.enums.ProductStatus;

import lombok.Data;

/**
 * Post query.
 *
 * @author ssatwa
 * @date 4/10/19
 */
@Data
public class ProductQuery {

	/**
	 * Keyword.
	 */
	private String keyword;

	/**
	 * Post status.
	 */
	private ProductStatus status;

	/**
	 * Category id.
	 */
	private Integer categoryId;

}
