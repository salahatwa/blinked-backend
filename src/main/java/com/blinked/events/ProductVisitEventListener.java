package com.blinked.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blinked.services.ProductService;

/**
 * Visit event listener.
 *
 * @author ssatwa
 * @date 19-4-22
 */
@Component
public class ProductVisitEventListener extends AbstractVisitEventListener {

	public ProductVisitEventListener(ProductService postService) {
		super(postService);
	}

	@Async
	@EventListener
	public void onProductVisitEvent(ProductVisitEvent event) throws InterruptedException {
		handleVisitEvent(event);
	}
}
