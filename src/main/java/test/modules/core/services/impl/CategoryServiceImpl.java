package test.modules.core.services.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.blinked.modules.core.exceptions.AlreadyExistsException;
import com.blinked.modules.core.exceptions.NotFoundException;
import com.blinked.modules.core.model.dto.CategoryDTO;
import com.blinked.modules.core.model.entities.Category;
import com.blinked.modules.core.model.vo.CategoryVO;
import com.blinked.modules.core.repositories.CategoryRepository;
import com.blinked.modules.core.services.CategoryService;
import com.blinked.modules.core.services.OptionService;
import com.blinked.modules.core.services.PostCategoryService;
import com.blinked.modules.core.services.base.AbstractCrudService;
import com.blinked.modules.core.utils.ServiceUtils;
import com.google.common.base.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * CategoryService implementation class.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Slf4j
@Service
public class CategoryServiceImpl extends AbstractCrudService<Category, Integer> implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final PostCategoryService postCategoryService;

	private final OptionService optionService;

	public CategoryServiceImpl(CategoryRepository categoryRepository, PostCategoryService postCategoryService,
			OptionService optionService) {
		super(categoryRepository);
		this.categoryRepository = categoryRepository;
		this.postCategoryService = postCategoryService;
		this.optionService = optionService;
	}

	@Override
	@Transactional
	public Category create(Category category) {
		Assert.notNull(category, "Category to create must not be null");

		// Check the category name
		long count = categoryRepository.countByName(category.getName());

		if (count > 0) {
			log.error("Category has exist already: [{}]", category);
			throw new AlreadyExistsException("This category already exists");
		}

		// Check parent id
		if (!ServiceUtils.isEmptyId(category.getParentId())) {
			count = categoryRepository.countById(category.getParentId());

			if (count == 0) {
				log.error("Parent category with id: [{}] was not found, category: [{}]", category.getParentId(),
						category);
				throw new NotFoundException("Parent category with id = " + category.getParentId() + " was not found");
			}
		}

		// Create it
		return super.create(category);
	}

	@Override
	public List<CategoryVO> listAsTree(Sort sort) {
		Assert.notNull(sort, "Sort info must not be null");

		// List all category
		List<Category> categories = listAll(sort);

		if (CollectionUtils.isEmpty(categories)) {
			return Collections.emptyList();
		}

		// Create top category
		CategoryVO topLevelCategory = createTopLevelCategory();

		// Concrete the tree
		concreteTree(topLevelCategory, categories);

		return topLevelCategory.getChildren();
	}

	/**
	 * Concrete category tree.
	 *
	 * @param parentCategory parent category vo must not be null
	 * @param categories     a list of category
	 */
	private void concreteTree(CategoryVO parentCategory, List<Category> categories) {
		Assert.notNull(parentCategory, "Parent category must not be null");

		if (CollectionUtils.isEmpty(categories)) {
			return;
		}

		// Get children for removing after
		List<Category> children = categories.stream()
				.filter(category -> Objects.equal(parentCategory.getId(), category.getParentId()))
				.collect(Collectors.toList());

		children.forEach(category -> {
			// Convert to child category vo
			CategoryVO child = new CategoryVO().convertFrom(category);
			// Init children if absent
			if (parentCategory.getChildren() == null) {
				parentCategory.setChildren(new LinkedList<>());
			}

			StringBuilder fullPath = new StringBuilder();

			if (optionService.isEnabledAbsolutePath()) {
				fullPath.append(optionService.getBlogBaseUrl());
			}

			// Add child
			parentCategory.getChildren().add(child);
		});

		// Remove all child categories
		categories.removeAll(children);

		// Foreach children vos
		if (!CollectionUtils.isEmpty(parentCategory.getChildren())) {
			parentCategory.getChildren().forEach(childCategory -> concreteTree(childCategory, categories));
		}
	}

	/**
	 * Creates a top level category.
	 *
	 * @return top level category with id 0
	 */
	@NonNull
	private CategoryVO createTopLevelCategory() {
		CategoryVO topCategory = new CategoryVO();
		// Set default value
		topCategory.setId(0);
		topCategory.setChildren(new LinkedList<>());
		topCategory.setParentId(-1);

		return topCategory;
	}

	@Override
	public Category getBySlug(String slug) {
		return categoryRepository.getBySlug(slug).orElse(null);
	}

	@Override
	public Category getBySlugOfNonNull(String slug) {
		return categoryRepository.getBySlug(slug)
				.orElseThrow(() -> new NotFoundException("查询不到该分类的信息").setErrorData(slug));
	}

	@Override
	public Category getByName(String name) {
		return categoryRepository.getByName(name).orElse(null);
	}

	@Override
	@Transactional
	public void removeCategoryAndPostCategoryBy(Integer categoryId) {
		List<Category> categories = listByParentId(categoryId);
		if (null != categories && categories.size() > 0) {
			categories.forEach(category -> {
				category.setParentId(0);
				update(category);
			});
		}
		// Remove category
		removeById(categoryId);
		// Remove post categories
		postCategoryService.removeByCategoryId(categoryId);
	}

	@Override
	public List<Category> listByParentId(Integer id) {
		Assert.notNull(id, "Parent id must not be null");
		return categoryRepository.findByParentId(id);
	}

	@Override
	public CategoryDTO convertTo(Category category) {
		Assert.notNull(category, "Category must not be null");

		CategoryDTO categoryDTO = new CategoryDTO().convertFrom(category);

		StringBuilder fullPath = new StringBuilder();

		if (optionService.isEnabledAbsolutePath()) {
			fullPath.append(optionService.getBlogBaseUrl());
		}

		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> convertTo(List<Category> categories) {
		if (CollectionUtils.isEmpty(categories)) {
			return Collections.emptyList();
		}

		return categories.stream().map(this::convertTo).collect(Collectors.toList());
	}
}
