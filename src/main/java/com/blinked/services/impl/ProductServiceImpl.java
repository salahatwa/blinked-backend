package com.blinked.services.impl;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.api.common.exception.NotFoundException;
import com.api.common.utils.DateUtils;
import com.api.common.utils.ServiceUtils;
import com.blinked.apis.requests.ProductQuery;
import com.blinked.apis.responses.ArchiveMonthVO;
import com.blinked.apis.responses.ArchiveYearVO;
import com.blinked.apis.responses.ProductDetailVO;
import com.blinked.apis.responses.ProductListVO;
import com.blinked.entities.Category;
import com.blinked.entities.Product;
import com.blinked.entities.ProductCategory;
import com.blinked.entities.ProductMeta;
import com.blinked.entities.ProductRate;
import com.blinked.entities.ProductTag;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.BaseProductMinimalDTO;
import com.blinked.entities.dto.BaseProductSimpleDTO;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.events.ProductVisitEvent;
import com.blinked.repositories.BaseProductRepository;
import com.blinked.repositories.ProductRepository;
import com.blinked.services.CategoryService;
import com.blinked.services.ProductCategoryService;
import com.blinked.services.ProductMetaService;
import com.blinked.services.ProductRateService;
import com.blinked.services.ProductService;
import com.blinked.services.ProductTagService;
import com.blinked.services.TagService;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Product service implementation.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Slf4j
@Service
public class ProductServiceImpl extends BaseProductServiceImpl<Product> implements ProductService {

	private final ProductRepository productRepository;

	private final TagService tagService;

	private final CategoryService categoryService;

	private final ProductTagService productTagService;

	private final ProductCategoryService productCategoryService;

	private final ProductRateService productRateService;

	private final ProductMetaService productMetaService;

	private final ApplicationEventPublisher eventPublisher;

	public ProductServiceImpl(BaseProductRepository<Product> baseProductRepository, ProductRepository productRepository,
			TagService tagService, CategoryService categoryService, ProductTagService productTagService,
			ProductCategoryService productCategoryService, ProductRateService productRateService,
			ProductMetaService productMetaService, ApplicationEventPublisher eventPublisher) {
		super(baseProductRepository);
		this.productRepository = productRepository;
		this.tagService = tagService;
		this.categoryService = categoryService;
		this.productTagService = productTagService;
		this.productCategoryService = productCategoryService;
		this.productRateService = productRateService;
		this.productMetaService = productMetaService;
		this.eventPublisher = eventPublisher;
	}

	public List<ProductListVO> topProducts(Pageable pageable) {
		return convertToListVo(productRepository.findAllByStatusAndTopPriority(ProductStatus.PUBLISHED, 1, pageable))
				.toList();
	}

	@Override
	public Page<Product> pageBy(ProductQuery productQuery, Pageable pageable) {
		Assert.notNull(productQuery, "Product query must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Build specification and find all
		return productRepository.findAll(buildSpecByQuery(productQuery), pageable);
	}

	@Override
	public Page<Product> pageBy(String keyword, Pageable pageable) {
		Assert.notNull(keyword, "keyword must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		ProductQuery productQuery = new ProductQuery();
		productQuery.setKeyword(keyword);
		productQuery.setStatus(ProductStatus.PUBLISHED);

		// Build specification and find all
		return productRepository.findAll(buildSpecByQuery(productQuery), pageable);
	}

	@Override
	@Transactional
	public ProductDetailVO createBy(Product productToCreate, Set<Integer> tagIds, Set<String> categoryIds,
			Set<ProductMeta> metas, boolean autoSave) {
		ProductDetailVO createdProduct = createOrUpdate(productToCreate, tagIds, categoryIds, metas);

		return createdProduct;
	}

	@Override
	public ProductDetailVO createBy(Product productToCreate, Set<Integer> tagIds, Set<String> categoryIds,
			boolean autoSave) {
		ProductDetailVO createdProduct = createOrUpdate(productToCreate, tagIds, categoryIds, null);

		return createdProduct;
	}

	@Override
	@Transactional
	public ProductDetailVO updateBy(Product productToUpdate, Set<Integer> tagIds, Set<String> categoryIds,
			Set<ProductMeta> metas, boolean autoSave) {
		// Set edit time
		productToUpdate.setEditTime(DateUtils.now());
		ProductDetailVO updatedProduct = createOrUpdate(productToUpdate, tagIds, categoryIds, metas);

		return updatedProduct;
	}

	@Override
	public Product getBy(ProductStatus status, String slug) {
		return super.getBy(status, slug);
	}

	@Override
	public Product getBy(Integer year, Integer month, String slug) {
		Assert.notNull(year, "Product create year must not be null");
		Assert.notNull(month, "Product create month must not be null");
		Assert.notNull(slug, "Product slug must not be null");

		Optional<Product> productOptional = productRepository.findBy(year, month, slug);

		return productOptional.orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(slug));
	}

	@NonNull
	@Override
	public Product getBy(@NonNull Integer year, @NonNull String slug) {
		Assert.notNull(year, "Product create year must not be null");
		Assert.notNull(slug, "Product slug must not be null");

		Optional<Product> productOptional = productRepository.findBy(year, slug);

		return productOptional.orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(slug));
	}

	@Override
	public Product getBy(Integer year, Integer month, String slug, ProductStatus status) {
		Assert.notNull(year, "Product create year must not be null");
		Assert.notNull(month, "Product create month must not be null");
		Assert.notNull(slug, "Product slug must not be null");
		Assert.notNull(status, "Product status must not be null");

		Optional<Product> productOptional = productRepository.findBy(year, month, slug, status);

		return productOptional.orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(slug));
	}

	@Override
	public Product getBy(Integer year, Integer month, Integer day, String slug) {
		Assert.notNull(year, "Product create year must not be null");
		Assert.notNull(month, "Product create month must not be null");
		Assert.notNull(day, "Product create day must not be null");
		Assert.notNull(slug, "Product slug must not be null");

		Optional<Product> productOptional = productRepository.findBy(year, month, day, slug);

		return productOptional.orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(slug));
	}

	@Override
	public Product getBy(Integer year, Integer month, Integer day, String slug, ProductStatus status) {
		Assert.notNull(year, "Product create year must not be null");
		Assert.notNull(month, "Product create month must not be null");
		Assert.notNull(day, "Product create day must not be null");
		Assert.notNull(slug, "Product slug must not be null");
		Assert.notNull(status, "Product status must not be null");

		Optional<Product> productOptional = productRepository.findBy(year, month, day, slug, status);

		return productOptional.orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(slug));
	}

	@Override
	public List<Product> removeByIds(Collection<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return ids.stream().map(this::removeById).collect(Collectors.toList());
	}

	@Override
	public Product getBySlug(String slug) {
		return super.getBySlug(slug);
	}

	@Override
	public List<ArchiveYearVO> listYearArchives() {
		// Get all products
		List<Product> products = productRepository.findAllByStatus(ProductStatus.PUBLISHED,
				Sort.by(DESC, "createTime"));

		return convertToYearArchives(products);
	}

	@Override
	public List<ArchiveMonthVO> listMonthArchives() {
		// Get all products
		List<Product> products = productRepository.findAllByStatus(ProductStatus.PUBLISHED,
				Sort.by(DESC, "createTime"));

		return convertToMonthArchives(products);
	}

	@Override
	public List<ArchiveYearVO> convertToYearArchives(List<Product> products) {
		Map<Integer, List<Product>> yearProductMap = new HashMap<>(8);

		products.forEach(product -> {
			Calendar calendar = DateUtils.convertTo(product.getCreateTime());
			yearProductMap.computeIfAbsent(calendar.get(Calendar.YEAR), year -> new LinkedList<>()).add(product);
		});

		List<ArchiveYearVO> archives = new LinkedList<>();

		yearProductMap.forEach((year, productList) -> {
			// Build archive
			ArchiveYearVO archive = new ArchiveYearVO();
			archive.setYear(year);
			archive.setProducts(convertToListVo(productList));

			// Add archive
			archives.add(archive);
		});

		// Sort this list
		archives.sort(new ArchiveYearVO.ArchiveComparator());

		return archives;
	}

	@Override
	public List<ArchiveMonthVO> convertToMonthArchives(List<Product> products) {

		Map<Integer, Map<Integer, List<Product>>> yearMonthProductMap = new HashMap<>(8);

		products.forEach(product -> {
			Calendar calendar = DateUtils.convertTo(product.getCreateTime());

			yearMonthProductMap.computeIfAbsent(calendar.get(Calendar.YEAR), year -> new HashMap<>())
					.computeIfAbsent(calendar.get(Calendar.MONTH) + 1, month -> new LinkedList<>()).add(product);
		});

		List<ArchiveMonthVO> archives = new LinkedList<>();

		yearMonthProductMap.forEach((year, monthProductMap) -> monthProductMap.forEach((month, productList) -> {
			ArchiveMonthVO archive = new ArchiveMonthVO();
			archive.setYear(year);
			archive.setMonth(month);
			archive.setProducts(convertToListVo(productList));

			archives.add(archive);
		}));

		// Sort this list
		archives.sort(new ArchiveMonthVO.ArchiveComparator());

		return archives;
	}

	@Override
	public String exportMarkdown(Integer id) {
		Assert.notNull(id, "Product id must not be null");
		Product product = getById(id);
		return exportMarkdown(product);
	}

	@Override
	public String exportMarkdown(Product product) {
		Assert.notNull(product, "Product must not be null");

		StringBuilder content = new StringBuilder("---\n");

		content.append("type: ").append("product").append("\n");
		content.append("title: ").append(product.getTitle()).append("\n");
		content.append("permalink: ").append(product.getSlug()).append("\n");
		content.append("thumbnail: ").append(product.getThumbnail()).append("\n");
		content.append("status: ").append(product.getStatus()).append("\n");
		content.append("date: ").append(product.getCreateTime()).append("\n");
		content.append("updated: ").append(product.getEditTime()).append("\n");
		content.append("comments: ").append(!product.getDisallowRate()).append("\n");

		List<Tag> tags = productTagService.listTagsBy(product.getId());

		if (tags.size() > 0) {
			content.append("tags:").append("\n");
			for (Tag tag : tags) {
				content.append("  - ").append(tag.getName()).append("\n");
			}
		}

		List<Category> categories = productCategoryService.listCategoriesBy(product.getId());

		if (categories.size() > 0) {
			content.append("categories:").append("\n");
			for (Category category : categories) {
				content.append("  - ").append(category.getName()).append("\n");
			}
		}

		List<ProductMeta> metas = productMetaService.listBy(product.getId());

		if (metas.size() > 0) {
			content.append("metas:").append("\n");
			for (ProductMeta productMeta : metas) {
				content.append("  - ").append(productMeta.getKey()).append(" :  ").append(productMeta.getValue())
						.append("\n");
			}
		}

		content.append("---\n\n");
		content.append(product.getTemplate());
		return content.toString();
	}

	@Override
	public ProductDetailVO convertToDetailVo(Product product) {
		// List tags
		List<Tag> tags = productTagService.listTagsBy(product.getId());
		// List categories
		List<Category> categories = productCategoryService.listCategoriesBy(product.getId());
		// List metas
		List<ProductMeta> metas = productMetaService.listBy(product.getId());
		// Convert to detail vo
		return convertTo(product, tags, categories, metas);
	}

	@Override
	public Product removeById(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");

		log.debug("Removing product: [{}]", productId);

		// Remove product tags
		List<ProductTag> productTags = productTagService.removeByProductId(productId);

		log.debug("Removed product tags: [{}]", productTags);

		// Remove product categories
		List<ProductCategory> productCategories = productCategoryService.removeByProductId(productId);

		log.debug("Removed product categories: [{}]", productCategories);

		// Remove metas
		List<ProductMeta> metas = productMetaService.removeByProductId(productId);
		log.debug("Removed product metas: [{}]", metas);

		// Remove product comments
		List<ProductRate> productComments = productRateService.removeByProductId(productId);
		log.debug("Removed product comments: [{}]", productComments);

		Product deletedProduct = super.removeById(productId);

		return deletedProduct;
	}

	@Override
	public Page<ProductListVO> convertToListVo(Page<Product> productPage) {
		Assert.notNull(productPage, "Product page must not be null");

		List<Product> products = productPage.getContent();

		Set<Integer> productIds = ServiceUtils.fetchProperty(products, Product::getId);

		// Get tag list map
		Map<Integer, List<Tag>> tagListMap = productTagService.listTagListMapBy(productIds);

		// Get category list map
		Map<Integer, List<Category>> categoryListMap = productCategoryService.listCategoryListMap(productIds);

		// Get comment count
		Map<Integer, Long> commentCountMap = productRateService.countByProductIds(productIds);

		// Get product meta list map
		Map<Integer, List<ProductMeta>> productMetaListMap = productMetaService.listProductMetaAsMap(productIds);

		return productPage.map(product -> {
			ProductListVO productListVO = new ProductListVO().convertFrom(product);

			Optional.ofNullable(tagListMap.get(product.getId())).orElseGet(LinkedList::new);

			// Set tags
			productListVO.setTags(Optional.ofNullable(tagListMap.get(product.getId())).orElseGet(LinkedList::new)
					.stream().filter(Objects::nonNull).map(tagService::convertTo).collect(Collectors.toList()));

			// Set categories
			productListVO.setCategories(
					Optional.ofNullable(categoryListMap.get(product.getId())).orElseGet(LinkedList::new).stream()
							.filter(Objects::nonNull).map(categoryService::convertTo).collect(Collectors.toList()));

			// Set product metas
			List<ProductMeta> metas = Optional.ofNullable(productMetaListMap.get(product.getId()))
					.orElseGet(LinkedList::new);
			productListVO.setMetas(productMetaService.convertToMap(metas));

			// Set comment count
			productListVO.setCommentCount(commentCountMap.getOrDefault(product.getId(), 0L));

			return productListVO;
		});
	}

	@Override
	public List<ProductListVO> convertToListVo(List<Product> products) {
		Assert.notNull(products, "Product page must not be null");

		Set<Integer> productIds = ServiceUtils.fetchProperty(products, Product::getId);

		// Get tag list map
		Map<Integer, List<Tag>> tagListMap = productTagService.listTagListMapBy(productIds);

		// Get category list map
		Map<Integer, List<Category>> categoryListMap = productCategoryService.listCategoryListMap(productIds);

		// Get comment count
		Map<Integer, Long> commentCountMap = productRateService.countByProductIds(productIds);

		// Get product meta list map
		Map<Integer, List<ProductMeta>> productMetaListMap = productMetaService.listProductMetaAsMap(productIds);

		return products.stream().map(product -> {
			ProductListVO productListVO = new ProductListVO().convertFrom(product);

			Optional.ofNullable(tagListMap.get(product.getId())).orElseGet(LinkedList::new);

			// Set tags
			productListVO.setTags(Optional.ofNullable(tagListMap.get(product.getId())).orElseGet(LinkedList::new)
					.stream().filter(Objects::nonNull).map(tagService::convertTo).collect(Collectors.toList()));

			// Set categories
			productListVO.setCategories(
					Optional.ofNullable(categoryListMap.get(product.getId())).orElseGet(LinkedList::new).stream()
							.filter(Objects::nonNull).map(categoryService::convertTo).collect(Collectors.toList()));

			// Set product metas
			List<ProductMeta> metas = Optional.ofNullable(productMetaListMap.get(product.getId()))
					.orElseGet(LinkedList::new);
			productListVO.setMetas(productMetaService.convertToMap(metas));

			// Set comment count
			productListVO.setCommentCount(commentCountMap.getOrDefault(product.getId(), 0L));

			return productListVO;
		}).collect(Collectors.toList());
	}

	@Override
	public Page<ProductDetailVO> convertToDetailVo(Page<Product> productPage) {
		Assert.notNull(productPage, "Product page must not be null");
		return productPage.map(this::convertToDetailVo);
	}

	@Override
	public BaseProductMinimalDTO convertToMinimal(Product product) {
		Assert.notNull(product, "Product must not be null");
		BaseProductMinimalDTO baseProductMinimalDTO = new BaseProductMinimalDTO().convertFrom(product);

		return baseProductMinimalDTO;
	}

	@Override
	public List<BaseProductMinimalDTO> convertToMinimal(List<Product> products) {
		if (CollectionUtils.isEmpty(products)) {
			return Collections.emptyList();
		}

		return products.stream().map(this::convertToMinimal).collect(Collectors.toList());
	}

	@Override
	public BaseProductSimpleDTO convertToSimple(Product product) {
		Assert.notNull(product, "Product must not be null");

		BaseProductSimpleDTO baseProductSimpleDTO = new BaseProductSimpleDTO().convertFrom(product);

		return baseProductSimpleDTO;
	}

	/**
	 * Converts to product detail vo.
	 *
	 * @param product         product must not be null
	 * @param tags            tags
	 * @param categories      categories
	 * @param productMetaList productMetaList
	 * @return product detail vo
	 */
	@NonNull
	private ProductDetailVO convertTo(@NonNull Product product, @Nullable List<Tag> tags,
			@Nullable List<Category> categories, List<ProductMeta> productMetaList) {
		Assert.notNull(product, "Product must not be null");

		// Convert to base detail vo
		ProductDetailVO productDetailVO = new ProductDetailVO().convertFrom(product);

		// Extract ids
		Set<Integer> tagIds = ServiceUtils.fetchProperty(tags, Tag::getId);
		Set<String> categoryIds = ServiceUtils.fetchProperty(categories, Category::getId);
		Set<Long> metaIds = ServiceUtils.fetchProperty(productMetaList, ProductMeta::getId);

		// Get product tag ids
		productDetailVO.setTagIds(tagIds);
		productDetailVO.setTags(tagService.convertTo(tags));

		// Get product category ids
		productDetailVO.setCategoryIds(categoryIds);
		productDetailVO.setCategories(categoryService.convertTo(categories));

		// Get product meta ids
		productDetailVO.setMetaIds(metaIds);
		productDetailVO.setMetas(productMetaService.convertTo(productMetaList));

		productDetailVO.setRateCount(productRateService.countByProductId(product.getId()));

		return productDetailVO;
	}

	/**
	 * Build specification by product query.
	 *
	 * @param productQuery product query must not be null
	 * @return a product specification
	 */
	@NonNull
	private Specification<Product> buildSpecByQuery(@NonNull ProductQuery productQuery) {
		Assert.notNull(productQuery, "Product query must not be null");

		return (Specification<Product>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (productQuery.getStatus() != null) {
				predicates.add(criteriaBuilder.equal(root.get("status"), productQuery.getStatus()));
			}

			if (productQuery.getCategoryId() != null) {
				Subquery<Product> productSubquery = query.subquery(Product.class);
				Root<ProductCategory> productCategoryRoot = productSubquery.from(ProductCategory.class);
				productSubquery.select(productCategoryRoot.get("productId"));
				productSubquery.where(criteriaBuilder.equal(root.get("id"), productCategoryRoot.get("productId")),
						criteriaBuilder.equal(productCategoryRoot.get("categoryId"), productQuery.getCategoryId()));
				predicates.add(criteriaBuilder.exists(productSubquery));
			}

			if (productQuery.getKeyword() != null) {
				// Format like condition
				String likeCondition = String.format("%%%s%%", StringUtils.strip(productQuery.getKeyword()));

				// Build like predicate
				Predicate titleLike = criteriaBuilder.like(root.get("title"), likeCondition);
				Predicate originalContentLike = criteriaBuilder.like(root.get("originalContent"), likeCondition);

				predicates.add(criteriaBuilder.or(titleLike, originalContentLike));
			}

			return query.where(predicates.toArray(new Predicate[0])).getRestriction();
		};
	}

	private ProductDetailVO createOrUpdate(@NonNull Product product, Set<Integer> tagIds, Set<String> categoryIds,
			Set<ProductMeta> metas) {
		Assert.notNull(product, "Product param must not be null");

		// Create or update product
		product = super.createOrUpdateBy(product);

		productTagService.removeByProductId(product.getId());

		productCategoryService.removeByProductId(product.getId());

		// List all tags
		List<Tag> tags = tagService.listAllByIds(tagIds);

		// List all categories
		List<Category> categories = categoryService.listAllByIds(categoryIds);

		// Create product tags
		List<ProductTag> productTags = productTagService.mergeOrCreateByIfAbsent(product.getId(),
				ServiceUtils.fetchProperty(tags, Tag::getId));

		log.info("Created product tags: [{}]", productTags);

		// Create product categories
		List<ProductCategory> productCategories = productCategoryService.mergeOrCreateByIfAbsent(product.getId(),
				ServiceUtils.fetchProperty(categories, Category::getId));

		log.info("Created product categories: [{}]", productCategories);

		// Create product meta data
		List<ProductMeta> productMetaList = productMetaService.createOrUpdateByProductId(product.getId(), metas);
		log.info("Created product metas: [{}]", productMetaList);

		// Convert to product detail vo
		return convertTo(product, tags, categories, productMetaList);
	}

	@Override
	public void publishVisitEvent(Integer productId) {
		eventPublisher.publishEvent(new ProductVisitEvent(this, productId));
	}

	@Override
	public @NotNull Sort getProductDefaultSort() {
		return Sort.by(DESC, "topPriority").and(Sort.by(DESC, "createTime").and(Sort.by(DESC, "id")));
	}

	@Override
	public List<Product> topView() {

		Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "visits"));

		Page<Product> page = productRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();

			return predicate;
		}, pageable);
		return page.getContent();
	}

	@Override
	public Object updateSequence(String table) {
		return productCategoryService.updateSequence(table);
	}

}
