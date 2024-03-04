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
import com.blinked.entities.Category;
import com.blinked.entities.Product;
import com.blinked.entities.ProductCategory;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.entities.projection.CategoryProductCountProjection;
import com.blinked.entities.projection.CategoryWithProductCountProjection;
import com.blinked.repositories.CategoryRepository;
import com.blinked.repositories.ProductCategoryRepository;
import com.blinked.repositories.ProductRepository;
import com.blinked.services.ProductCategoryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * Product category service implementation.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
@Service
public class ProductCategoryServiceImpl extends AbstractCrudService<ProductCategory, Integer>
		implements ProductCategoryService {

	private final ProductCategoryRepository productCategoryRepository;

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository,
			ProductRepository productRepository, CategoryRepository categoryRepository) {
		super(productCategoryRepository);
		this.productCategoryRepository = productCategoryRepository;
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> listCategoriesBy(Integer product) {
		Assert.notNull(product, "Product id must not be null");

		// Find all category ids
		Set<String> categoryIds = productCategoryRepository.findAllCategoryIdsByProductId(product);

		return categoryRepository.findAllById(categoryIds);
	}

	@Override
	public Map<Integer, List<Category>> listCategoryListMap(Collection<Integer> products) {
		if (CollectionUtils.isEmpty(products)) {
			return Collections.emptyMap();
		}

		// Find all post categories
		List<ProductCategory> postCategories = productCategoryRepository.findAllByProductIdIn(products);

		// Fetch category ids
		Set<String> categoryIds = ServiceUtils.fetchProperty(postCategories, ProductCategory::getCategoryId);

		// Find all categories
		List<Category> categories = categoryRepository.findAllById(categoryIds);

		// Convert to category map
		Map<String, Category> categoryMap = ServiceUtils.convertToMap(categories, Category::getId);

		// Create category list map
		Map<Integer, List<Category>> categoryListMap = new HashMap<>();

		// Foreach and collect
		postCategories.forEach(postCategory -> categoryListMap
				.computeIfAbsent(postCategory.getProductId(), product -> new LinkedList<>())
				.add(categoryMap.get(postCategory.getCategoryId())));

		return categoryListMap;
	}

	@Override
	public List<Product> listProductBy(String categoryId) {
		Assert.notNull(categoryId, "Category id must not be null");

		// Find all post ids
		Set<Integer> products = productCategoryRepository.findAllProductIdsByCategoryId(categoryId);

		return productRepository.findAllById(products);
	}

	@Override
	public List<Product> listProductByCat(String categoryId, ProductStatus status) {
		Assert.notNull(categoryId, "Category id must not be null");
		Assert.notNull(status, "Product status must not be null");

		// Find all post ids
		Set<Integer> products = productCategoryRepository.findAllProductIdsByCategoryId(categoryId, status);

		return productRepository.findAllById(products);
	}

	@Override
	public List<Product> listProductBy(String slug, ProductStatus status) {
		Assert.notNull(slug, "Category slug must not be null");
		Assert.notNull(status, "Product status must not be null");

		Category category = categoryRepository.getBySlug(slug).orElseThrow(
				() -> new NotFoundException("No information in this category can be found").setErrorData(slug));

		Set<Integer> postsIds = productCategoryRepository.findAllProductIdsByCategoryId(category.getId(), status);

		return productRepository.findAllById(postsIds);
	}

	@Override
	public Page<Product> pageProductBy(String categoryId, Pageable pageable) {
		Assert.notNull(categoryId, "Category id must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Find all post ids
		Set<Integer> products = productCategoryRepository.findAllProductIdsByCategoryId(categoryId);

		return productRepository.findAllByIdIn(products, pageable);
	}

	@Override
	public Page<Product> pageProductBy(String categoryId, ProductStatus status, Pageable pageable) {
		Assert.notNull(categoryId, "Category id must not be null");
		Assert.notNull(status, "Product status must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Find all post ids
		Set<Integer> products = productCategoryRepository.findAllProductIdsByCategoryId(categoryId, status);

		return productRepository.findAllByIdIn(products, pageable);
	}

	@Override
	public List<ProductCategory> mergeOrCreateByIfAbsent(Integer product, Set<String> categoryIds) {
		Assert.notNull(product, "Product id must not be null");

		if (CollectionUtils.isEmpty(categoryIds)) {
			return Collections.emptyList();
		}

		// Build post categories
		List<ProductCategory> postCategoriesStaging = categoryIds.stream().map(categoryId -> {
			ProductCategory postCategory = new ProductCategory();
			postCategory.setProductId(product);
			postCategory.setCategoryId(categoryId);
			return postCategory;
		}).collect(Collectors.toList());

		List<ProductCategory> postCategoriesToCreate = new LinkedList<>();
		List<ProductCategory> postCategoriesToRemove = new LinkedList<>();

		// Find all exist post categories
		System.out.println("Categories:" + categoryIds + ":POST ID:" + product);
		List<ProductCategory> postCategories = productCategoryRepository.findAllByProductId(product);

		System.out.println("==============ProductCategories==================");
		System.out.println(postCategories.size());

		postCategories.forEach(postCategory -> {
			if (!postCategoriesStaging.contains(postCategory)) {
				postCategoriesToRemove.add(postCategory);
			}
		});

		System.out.println("==============ProductCategoriesStaging==================");
		System.out.println(postCategoriesStaging);

		postCategoriesStaging.forEach(postCategoryStaging -> {
			if (!postCategories.contains(postCategoryStaging)) {
				postCategoriesToCreate.add(postCategoryStaging);
			}
		});

		System.out.println("REMOVE===========:" + postCategoriesToRemove);
		System.out.println("CREATE===========:" + postCategoriesToCreate);
		// Remove post categories
		removeAll(postCategoriesToRemove);

		// Remove all post categories need to remove
		postCategories.removeAll(postCategoriesToRemove);

		System.out.println(postCategoriesToCreate.size() + "=====" + (postCategoriesToCreate.size() > 0));
		postCategories.addAll(createInBatch(postCategoriesToCreate));

		// Create them
		return postCategories;
	}

	@Override
	public List<ProductCategory> listByProductId(Integer product) {
		Assert.notNull(product, "Product id must not be null");

		return productCategoryRepository.findAllByProductId(product);
	}

	@Override
	public List<ProductCategory> listByCategoryId(String categoryId) {
		Assert.notNull(categoryId, "Category id must not be null");

		return productCategoryRepository.findAllByCategoryId(categoryId);
	}

	@Override
	public Set<String> listCategoryIdsByProductId(Integer product) {
		Assert.notNull(product, "Product id must not be null");

		return productCategoryRepository.findAllCategoryIdsByProductId(product);
	}

	@Override
	public List<ProductCategory> removeByProductId(Integer product) {
		Assert.notNull(product, "Product id must not be null");

		return productCategoryRepository.deleteByProductId(product);
	}

	@Override
	public List<ProductCategory> removeByCategoryId(String categoryId) {
		Assert.notNull(categoryId, "Category id must not be null");

		return productCategoryRepository.deleteByCategoryId(categoryId);
	}

	@Override
	public List<CategoryWithProductCountProjection> listCategoryWithProductCountDto(Sort sort) {
		Assert.notNull(sort, "Sort info must not be null");

		List<Category> categories = categoryRepository.findAll(sort);

		// Query category post count
		Map<String, Long> categoryProductCountMap = ServiceUtils.convertToMap(
				productCategoryRepository.findProductCount(), CategoryProductCountProjection::getCategoryId,
				CategoryProductCountProjection::getProductCount);

		// Convert and return
		return categories.stream().map(category -> {
			// Create category post count dto
			CategoryWithProductCountProjection categoryWithProductCountDTO = new CategoryWithProductCountProjection()
					.convertFrom(category);
			// Set post count
			categoryWithProductCountDTO.setProductCount(categoryProductCountMap.getOrDefault(category.getId(), 0L));

			StringBuilder fullPath = new StringBuilder();

//			if (optionService.isEnabledAbsolutePath()) {
//				fullPath.append(optionService.getBlogBaseUrl());
//			}
//
//			fullPath.append(URL_SEPARATOR).append(optionService.getCategoriesPrefix()).append(URL_SEPARATOR)
//					.append(category.getSlug()).append(optionService.getPathSuffix());

			categoryWithProductCountDTO.setFullPath(fullPath.toString());

			return categoryWithProductCountDTO;
		}).collect(Collectors.toList());
	}

	/**
	 * post_categories
	 * 
	 * @param table
	 * @return
	 */
	@Override
	public Object updateSequence(String table) {
		Assert.notNull(table, "Product id must not be null");

		Query query = entityManager.createNativeQuery("SELECT setval(pg_get_serial_sequence('" + table
				+ "', 'id'), coalesce(max(id)+1, 1), false) FROM " + table + ";");

		Object result = query.getResultList();
		System.out.println("Result================================::" + result);

		// Create them
		return result;
	}

}
