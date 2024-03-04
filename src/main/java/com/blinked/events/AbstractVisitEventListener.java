package com.blinked.events;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.util.Assert;

import com.blinked.services.BaseProductService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract visit event listener.
 *
 * @author ssatwa
 * @date 19-4-24
 */
@Slf4j
public abstract class AbstractVisitEventListener {

	private final Map<Integer, BlockingQueue<Integer>> visitQueueMap;

	private final Map<Integer, ProductVisitTask> visitTaskMap;

	private final BaseProductService basePostService;

	private final ExecutorService executor;

	protected AbstractVisitEventListener(BaseProductService basePostService) {
		this.basePostService = basePostService;

		int initCapacity = 8;

		long count = basePostService.count();

		if (count < initCapacity) {
			initCapacity = (int) count;
		}

		visitQueueMap = new ConcurrentHashMap<>(initCapacity << 1);
		visitTaskMap = new ConcurrentHashMap<>(initCapacity << 1);

		this.executor = Executors.newCachedThreadPool();
	}

	/**
	 * Handle visit event.
	 *
	 * @param event visit event must not be null
	 * @throws InterruptedException
	 */
	protected void handleVisitEvent(@NonNull AbstractVisitEvent event) throws InterruptedException {
		Assert.notNull(event, "Visit event must not be null");

		// Get product id
		Integer id = event.getId();

		log.debug("Received a visit event, product id: [{}]", id);

		// Get product visit queue
		BlockingQueue<Integer> productVisitQueue = visitQueueMap.computeIfAbsent(id, this::createEmptyQueue);

		visitTaskMap.computeIfAbsent(id, this::createProductVisitTask);

		// Put a visit for the product
		productVisitQueue.put(id);
	}

	private ProductVisitTask createProductVisitTask(Integer productId) {
		// Create new product visit task
		ProductVisitTask productVisitTask = new ProductVisitTask(productId);
		// Start a product visit task
		executor.execute(productVisitTask);

		log.debug("Created a new product visit task for product id: [{}]", productId);
		return productVisitTask;
	}

	private BlockingQueue<Integer> createEmptyQueue(Integer productId) {
		// Create a new queue
		return new LinkedBlockingQueue<>();
	}

	/**
	 * Post visit task.
	 */
	private class ProductVisitTask implements Runnable {

		private final Integer id;

		private ProductVisitTask(Integer id) {
			this.id = id;
		}

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					BlockingQueue<Integer> productVisitQueue = visitQueueMap.get(id);
					Integer productId = productVisitQueue.take();

					log.debug("Took a new visit for product id: [{}]", productId);

					// Increase the visit
					basePostService.increaseVisit(productId);

					log.debug("Increased visits for product id: [{}]", productId);
				} catch (InterruptedException e) {
					log.debug("Post visit task: " + Thread.currentThread().getName() + " was interrupted", e);
					// Ignore this exception
				}
			}

			log.debug("Thread: [{}] has been interrupted", Thread.currentThread().getName());
		}
	}
}
