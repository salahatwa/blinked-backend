package com.blinked.services.impl;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.blinked.entities.BaseProduct;
import com.blinked.entities.dto.BaseProductDetailDTO;
import com.blinked.entities.dto.BaseProductMinimalDTO;
import com.blinked.entities.dto.BaseProductSimpleDTO;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.exceptions.AlreadyExistsException;
import com.blinked.exceptions.BadRequestException;
import com.blinked.exceptions.NotFoundException;
import com.blinked.repositories.base.AbstractCrudService;
import com.blinked.repositories.base.BaseProductRepository;
import com.blinked.services.base.BaseProductService;
import com.blinked.utils.DateUtils;
import com.blinked.utils.ServiceUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Base product service implementation.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
@Slf4j
public abstract class BaseProductServiceImpl<PRODUCT extends BaseProduct> extends AbstractCrudService<PRODUCT, Integer>
		implements BaseProductService<PRODUCT> {

	private final BaseProductRepository<PRODUCT> baseProductRepository;

	public BaseProductServiceImpl(BaseProductRepository<PRODUCT> baseProductRepository) {
		super(baseProductRepository);
		this.baseProductRepository = baseProductRepository;
	}

	public List<PRODUCT> topProducts() {
		return baseProductRepository.findAllByStatusAndTopPriority(ProductStatus.PUBLISHED, 1);
	}

	@Override
	public long countVisit() {
		return Optional.ofNullable(baseProductRepository.countVisit()).orElse(0L);
	}

	@Override
	public long countLike() {
		return Optional.ofNullable(baseProductRepository.countLike()).orElse(0L);
	}

	@Override
	public long countByStatus(ProductStatus status) {
		Assert.notNull(status, "Product status must not be null");

		return baseProductRepository.countByStatus(status);
	}

	@Override
	public PRODUCT getBySlug(String slug) {
		Assert.hasText(slug, "Slug must not be blank");

		return baseProductRepository.getBySlug(slug).orElseThrow(
				() -> new NotFoundException("Can't find information about this product:" + slug).setErrorData(slug));
	}

	@Override
	public PRODUCT getBy(ProductStatus status, String slug) {
		Assert.notNull(status, "Product status must not be null");
		Assert.hasText(slug, "Product slug must not be blank");

		Optional<PRODUCT> productOptional = baseProductRepository.getBySlugAndStatus(slug, status);

		return productOptional.orElseThrow(
				() -> new NotFoundException("Can't find information about this product").setErrorData(slug));
	}

	@Override
	public PRODUCT getBy(ProductStatus status, Integer id) {
		Assert.notNull(status, "Product status must not be null");
		Assert.notNull(id, "Product id must not be null");

		Optional<PRODUCT> productOptional = baseProductRepository.getByIdAndStatus(id, status);

		return productOptional
				.orElseThrow(() -> new NotFoundException("Can't find information about this product").setErrorData(id));
	}

	@Override
	public List<PRODUCT> listAllBy(ProductStatus status) {
		Assert.notNull(status, "Product status must not be null");

		return baseProductRepository.findAllByStatus(status);
	}

	@Override
	public List<PRODUCT> listPrevProducts(PRODUCT product, int size) {
		Assert.notNull(product, "Product must not be null");

		String indexSort = "indexSort";
		PageRequest pageRequest = PageRequest.of(0, size, Sort.by(ASC, indexSort));

		switch (indexSort) {
		case "createTime":
			return baseProductRepository
					.findAllByStatusAndCreateTimeAfter(ProductStatus.PUBLISHED, product.getCreateTime(), pageRequest)
					.getContent();
		case "editTime":
			return baseProductRepository
					.findAllByStatusAndEditTimeAfter(ProductStatus.PUBLISHED, product.getEditTime(), pageRequest)
					.getContent();
		case "visits":
			return baseProductRepository
					.findAllByStatusAndVisitsAfter(ProductStatus.PUBLISHED, product.getVisits(), pageRequest)
					.getContent();
		default:
			return Collections.emptyList();
		}
	}

	@Override
	public List<PRODUCT> listNextProducts(PRODUCT product, int size) {
		Assert.notNull(product, "Product must not be null");

		String indexSort = "indexSort";

		PageRequest pageRequest = PageRequest.of(0, size, Sort.by(DESC, indexSort));

		switch (indexSort) {
		case "createTime":
			return baseProductRepository
					.findAllByStatusAndCreateTimeBefore(ProductStatus.PUBLISHED, product.getCreateTime(), pageRequest)
					.getContent();
		case "editTime":
			return baseProductRepository
					.findAllByStatusAndEditTimeBefore(ProductStatus.PUBLISHED, product.getEditTime(), pageRequest)
					.getContent();
		case "visits":
			return baseProductRepository
					.findAllByStatusAndVisitsBefore(ProductStatus.PUBLISHED, product.getVisits(), pageRequest)
					.getContent();
		default:
			return Collections.emptyList();
		}
	}

	@Override
	public Optional<PRODUCT> getPrevProduct(PRODUCT product) {
		List<PRODUCT> products = listPrevProducts(product, 1);

		return CollectionUtils.isEmpty(products) ? Optional.empty() : Optional.of(products.get(0));
	}

	@Override
	public Optional<PRODUCT> getNextProduct(PRODUCT product) {
		List<PRODUCT> products = listNextProducts(product, 1);

		return CollectionUtils.isEmpty(products) ? Optional.empty() : Optional.of(products.get(0));
	}

	@Override
	public Page<PRODUCT> pageLatest(int top) {
		Assert.isTrue(top > 0, "Top number must not be less than 0");

		PageRequest latestPageable = PageRequest.of(0, top, Sort.by(DESC, "createTime"));

		return listAll(latestPageable);
	}

	/**
	 * Lists latest products.
	 *
	 * @param top top number must not be less than 0
	 * @return latest products
	 */
	@Override
	public List<PRODUCT> listLatest(int top) {
		Assert.isTrue(top > 0, "Top number must not be less than 0");

		PageRequest latestPageable = PageRequest.of(0, top, Sort.by(DESC, "createTime"));
		return baseProductRepository.findAllByStatus(ProductStatus.PUBLISHED, latestPageable).getContent();
	}

	@Override
	public Page<PRODUCT> pageBy(Pageable pageable) {
		Assert.notNull(pageable, "Page info must not be null");

		return listAll(pageable);
	}

	@Override
	public Page<PRODUCT> pageBy(ProductStatus status, Pageable pageable) {
		Assert.notNull(status, "Product status must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		return baseProductRepository.findAllByStatus(status, pageable);
	}

	@Override
	@Transactional
	public void increaseVisit(long visits, Integer productId) {
		Assert.isTrue(visits > 0, "Visits to increase must not be less than 1");
		Assert.notNull(productId, "Product id must not be null");

		boolean finishedIncrease;
		if (baseProductRepository.getByIdAndStatus(productId, ProductStatus.DRAFT).isPresent()) {
			finishedIncrease = true;
			log.info("Product with id: [{}] is a draft and visits will not be updated", productId);
		} else {
			finishedIncrease = baseProductRepository.updateVisit(visits, productId) == 1;
		}

		if (!finishedIncrease) {
			log.error("Product with id: [{}] may not be found", productId);
			throw new BadRequestException("Failed to increase visits " + visits + " for product with id " + productId);
		}
	}

	@Override
	@Transactional
	public void increaseLike(long likes, Integer productId) {
		Assert.isTrue(likes > 0, "Likes to increase must not be less than 1");
		Assert.notNull(productId, "Product id must not be null");

		long affectedRows = baseProductRepository.updateLikes(likes, productId);

		if (affectedRows != 1) {
			log.error("Product with id: [{}] may not be found", productId);
			throw new BadRequestException("Failed to increase likes " + likes + " for product with id " + productId);
		}
	}

	@Override
	@Transactional
	public void increaseVisit(Integer productId) {
		increaseVisit(1L, productId);
	}

	@Override
	@Transactional
	public void increaseLike(Integer productId) {
		increaseLike(1L, productId);
	}

	@Override
	@Transactional
	public PRODUCT createOrUpdateBy(PRODUCT product) {
		Assert.notNull(product, "Product must not be null");

		// Create or update product
		if (ServiceUtils.isEmptyId(product.getId())) {
			// The sheet will be created
			return create(product);
		}

		// The sheet will be updated
		// Set edit time
		product.setEditTime(DateUtils.now());

		// Update it
		return update(product);
	}

	@Override
	public PRODUCT filterIfEncrypt(PRODUCT product) {
		Assert.notNull(product, "Product must not be null");

		return product;
	}

	@Override
	public BaseProductMinimalDTO convertToMinimal(PRODUCT product) {
		Assert.notNull(product, "Product must not be null");

		return new BaseProductMinimalDTO().convertFrom(product);
	}

	@Override
	public List<BaseProductMinimalDTO> convertToMinimal(List<PRODUCT> products) {
		if (CollectionUtils.isEmpty(products)) {
			return Collections.emptyList();
		}

		return products.stream().map(this::convertToMinimal).collect(Collectors.toList());
	}

	@Override
	public Page<BaseProductMinimalDTO> convertToMinimal(Page<PRODUCT> productPage) {
		Assert.notNull(productPage, "Product page must not be null");

		return productPage.map(this::convertToMinimal);
	}

	@Override
	public BaseProductSimpleDTO convertToSimple(PRODUCT product) {
		Assert.notNull(product, "Product must not be null");

		BaseProductSimpleDTO baseProductSimpleDTO = new BaseProductSimpleDTO().convertFrom(product);

		return baseProductSimpleDTO;
	}

	@Override
	public List<BaseProductSimpleDTO> convertToSimple(List<PRODUCT> products) {
		if (CollectionUtils.isEmpty(products)) {
			return Collections.emptyList();
		}

		return products.stream().map(this::convertToSimple).collect(Collectors.toList());
	}

	@Override
	public Page<BaseProductSimpleDTO> convertToSimple(Page<PRODUCT> productPage) {
		Assert.notNull(productPage, "Product page must not be null");

		return productPage.map(this::convertToSimple);
	}

	@Override
	public BaseProductDetailDTO convertToDetail(PRODUCT product) {
		Assert.notNull(product, "Product must not be null");

		return new BaseProductDetailDTO().convertFrom(product);
	}

	@Override
	@Transactional
	public PRODUCT updateTemplate(String content, Integer productId) {
		Assert.isTrue(!ServiceUtils.isEmptyId(productId), "Product id must not be empty");

		if (content == null) {
			content = "";
		}

		PRODUCT product = getById(productId);

		if (!StringUtils.equals(content, product.getTemplate())) {
			// If content is different with database, then update database
			int updatedRows = baseProductRepository.updateTemplate(content, productId);
			if (updatedRows != 1) {
				throw new ServiceException("Failed to update original content of product with id " + productId);
			}
			// Set the content
			product.setTemplate(content);
		}

		return product;
	}

	@Override
	@Transactional
	public PRODUCT updateStatus(ProductStatus status, Integer productId) {
		Assert.notNull(status, "Product status must not be null");
		Assert.isTrue(!ServiceUtils.isEmptyId(productId), "Product id must not be empty");

		// Get product
		PRODUCT product = getById(productId);

		if (!status.equals(product.getStatus())) {
			// Update product
			int updatedRows = baseProductRepository.updateStatus(status, productId);
			if (updatedRows != 1) {
				throw new ServiceException("Failed to update product status of product with id " + productId);
			}

			product.setStatus(status);
		}

		return product;
	}

	@Override
	@Transactional
	public List<PRODUCT> updateStatusByIds(List<Integer> ids, ProductStatus status) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return ids.stream().map(id -> {
			return updateStatus(status, id);
		}).collect(Collectors.toList());
	}

	@Override
	public String generateDescription(String content) {
		Assert.notNull(content, "html content must not be null");

		return StringUtils.substring(content, 0, 150);
	}

	@Override
	public PRODUCT create(PRODUCT product) {
		// Check title
		slugMustNotExist(product);

		return super.create(product);
	}

	@Override
	public PRODUCT update(PRODUCT product) {
		// Check title
		slugMustNotExist(product);

		return super.update(product);
	}

	/**
	 * Check if the slug is exist.
	 *
	 * @param product product must not be null
	 */
	protected void slugMustNotExist(@NonNull PRODUCT product) {
		Assert.notNull(product, "Product must not be null");

		// Get slug count
		boolean exist;

		if (ServiceUtils.isEmptyId(product.getId())) {
			// The sheet will be created
			exist = baseProductRepository.existsBySlug(product.getSlug());
		} else {
			// The sheet will be updated
			exist = baseProductRepository.existsByIdNotAndSlug(product.getId(), product.getSlug());
		}

		if (exist) {
			throw new AlreadyExistsException("Product " + product.getSlug() + " existed");
		}
	}

	@NonNull
	protected String generateSummary(@NonNull String htmlContent) {
		Assert.notNull(htmlContent, "html content must not be null");

		return StringUtils.substring(htmlContent, 0, 150);
	}
}
