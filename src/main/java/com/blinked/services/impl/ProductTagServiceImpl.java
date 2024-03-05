package com.blinked.services.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.api.common.exception.NotFoundException;
import com.api.common.repo.AbstractCrudService;
import com.api.common.utils.ServiceUtils;
import com.blinked.entities.Product;
import com.blinked.entities.ProductTag;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.TagWithProductCountDTO;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.entities.projection.TagProductCountProjection;
import com.blinked.repositories.ProductRepository;
import com.blinked.repositories.ProductTagRepository;
import com.blinked.repositories.TagRepository;
import com.blinked.services.ProductTagService;

/**
 * Product tag service implementation.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@Service
public class ProductTagServiceImpl extends AbstractCrudService<ProductTag, Integer> implements ProductTagService {

	private final ProductTagRepository productTagRepository;

	private final ProductRepository productRepository;

	private final TagRepository tagRepository;

	public ProductTagServiceImpl(ProductTagRepository productTagRepository, ProductRepository productRepository,
			TagRepository tagRepository) {
		super(productTagRepository);
		this.productTagRepository = productTagRepository;
		this.productRepository = productRepository;
		this.tagRepository = tagRepository;
	}

	@Override
	public List<Tag> listTagsBy(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");

		// Find all tag ids
		Set<String> tagIds = productTagRepository.findAllTagIdsByProductId(productId);

		return tagRepository.findAllById(tagIds);
	}

	@Override
	public List<TagWithProductCountDTO> listTagWithCountDtos(Sort sort) {
		Assert.notNull(sort, "Sort info must not be null");

		// Find all tags
		List<Tag> tags = tagRepository.findAll(sort);

		// Find all product count
		Map<String, Long> tagProductCountMap = ServiceUtils.convertToMap(productTagRepository.findProductCount(),
				TagProductCountProjection::getTagId, TagProductCountProjection::getProductCount);

		// Find product count
		return tags.stream().map(tag -> {
			TagWithProductCountDTO tagWithCountOutputDTO = new TagWithProductCountDTO().convertFrom(tag);
			tagWithCountOutputDTO.setProductCount(tagProductCountMap.getOrDefault(tag.getId(), 0L));

			StringBuilder fullPath = new StringBuilder();

			tagWithCountOutputDTO.setFullPath(fullPath.toString());

			return tagWithCountOutputDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public Map<Integer, List<Tag>> listTagListMapBy(Collection<Integer> productIds) {
		if (CollectionUtils.isEmpty(productIds)) {
			return Collections.emptyMap();
		}

		// Find all product tags
		List<ProductTag> productTags = productTagRepository.findAllByProductIdIn(productIds);

		// Fetch tag ids
		Set<String> tagIds = ServiceUtils.fetchProperty(productTags, ProductTag::getTagId);

		// Find all tags
		List<Tag> tags = tagRepository.findAllById(tagIds);

		// Convert to tag map
		Map<String, Tag> tagMap = ServiceUtils.convertToMap(tags, Tag::getId);

		// Create tag list map
		Map<Integer, List<Tag>> tagListMap = new HashMap<>();

		// Foreach and collect
		productTags.forEach(
				productTag -> tagListMap.computeIfAbsent(productTag.getProductId(), productId -> new LinkedList<>())
						.add(tagMap.get(productTag.getTagId())));

		return tagListMap;
	}

	@Override
	public List<Product> listProductsBy(String tagId) {
		Assert.notNull(tagId, "Tag id must not be null");

		// Find all product ids
		Set<Integer> productIds = productTagRepository.findAllProductIdsByTagId(tagId);

		return productRepository.findAllById(productIds);
	}

	@Override
	public List<Product> listProductsTagBy(String tagId, ProductStatus status) {
		Assert.notNull(tagId, "Tag id must not be null");
		Assert.notNull(status, "Product status must not be null");

		// Find all product ids
		Set<Integer> productIds = productTagRepository.findAllProductIdsByTagId(tagId, status);

		return productRepository.findAllById(productIds);
	}

	@Override
	public List<Product> listProductsBy(String slug, ProductStatus status) {
		Assert.notNull(slug, "Tag slug must not be null");
		Assert.notNull(status, "Product status must not be null");

		Tag tag = tagRepository.getBySlug(slug)
				.orElseThrow(() -> new NotFoundException("Cannot find information about this tag").setErrorData(slug));

		Set<Integer> productIds = productTagRepository.findAllProductIdsByTagId(tag.getId(), status);

		return productRepository.findAllById(productIds);
	}

	@Override
	public Page<Product> pageProductsBy(String tagId, Pageable pageable) {
		Assert.notNull(tagId, "Tag id must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Find all product ids
		Set<Integer> productIds = productTagRepository.findAllProductIdsByTagId(tagId);

		return productRepository.findAllByIdIn(productIds, pageable);
	}

	@Override
	public Page<Product> pageProductsBy(String tagId, ProductStatus status, Pageable pageable) {
		Assert.notNull(tagId, "Tag id must not be null");
		Assert.notNull(status, "Product status must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Find all product ids
		Set<Integer> productIds = productTagRepository.findAllProductIdsByTagId(tagId, status);

		return productRepository.findAllByIdIn(productIds, pageable);
	}

	@Override
	public List<ProductTag> mergeOrCreateByIfAbsent(Integer productId, Set<String> tagIds) {
		Assert.notNull(productId, "Product id must not be null");

		if (CollectionUtils.isEmpty(tagIds)) {
			return Collections.emptyList();
		}

		// Create product tags
		List<ProductTag> productTagsStaging = tagIds.stream().map(tagId -> {
			// Build product tag
			ProductTag productTag = new ProductTag();
			productTag.setProductId(productId);
			productTag.setTagId(tagId);
			return productTag;
		}).collect(Collectors.toList());

		List<ProductTag> productTagsToRemove = new LinkedList<>();
		List<ProductTag> productTagsToCreate = new LinkedList<>();

		List<ProductTag> productTags = productTagRepository.findAllByProductId(productId);

		productTags.forEach(productTag -> {
			if (!productTagsStaging.contains(productTag)) {
				productTagsToRemove.add(productTag);
			}
		});

		productTagsStaging.forEach(productTagStaging -> {
			if (!productTags.contains(productTagStaging)) {
				productTagsToCreate.add(productTagStaging);
			}
		});

		// Remove product tags
		removeAll(productTagsToRemove);

		// Remove all product tags need to remove
		productTags.removeAll(productTagsToRemove);

		// Add all created product tags
		productTags.addAll(createInBatch(productTagsToCreate));

		// Return product tags
		return productTags;
	}

	@Override
	public List<ProductTag> listByProductId(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");

		return productTagRepository.findAllByProductId(productId);
	}

	@Override
	public List<ProductTag> listByTagId(String tagId) {
		Assert.notNull(tagId, "Tag id must not be null");

		return productTagRepository.findAllByTagId(tagId);
	}

	@Override
	public Set<String> listTagIdsByProductId(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");

		return productTagRepository.findAllTagIdsByProductId(productId);
	}

	@Override
	public List<ProductTag> removeByProductId(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");

		return productTagRepository.deleteByProductId(productId);
	}

	@Override
	public List<ProductTag> removeByTagId(String tagId) {
		Assert.notNull(tagId, "Tag id must not be null");

		return productTagRepository.deleteByTagId(tagId);
	}
}
