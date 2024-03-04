package com.blinked.events;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import com.api.common.utils.ServiceUtils;

/**
 * Post visit event.
 *
 * @author ssatwa
 * @date 19-4-22
 */
public class ProductVisitEvent extends AbstractVisitEvent {

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source    the object on which the event initially occurred (never
	 *                  {@code null})
	 * @param productId post id must not be null
	 */
	public ProductVisitEvent(Object source, @NonNull Integer productId) {
		super(source, productId);
		Assert.isTrue(!ServiceUtils.isEmptyId(productId), "Product id must not be empty");
	}
}
