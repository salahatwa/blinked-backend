package com.blinked.services.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.api.common.exception.AlreadyExistsException;
import com.api.common.exception.NotFoundException;
import com.api.common.repo.AbstractCrudService;
import com.blinked.apis.responses.CategoryTreeVO;
import com.blinked.entities.Category;
import com.blinked.entities.dto.CategoryDTO;
import com.blinked.repositories.CategoryRepository;
import com.blinked.services.CategoryService;
import com.blinked.services.ProductCategoryService;
import com.google.common.base.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssatwa
 * @date 2024-03-14
 */
@Slf4j
@Service
public class CategoryServiceImpl extends AbstractCrudService<Category, String> implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final ProductCategoryService productCategoryService;

	public CategoryServiceImpl(CategoryRepository categoryRepository, ProductCategoryService productCategoryService) {
		super(categoryRepository);
		this.categoryRepository = categoryRepository;
		this.productCategoryService = productCategoryService;
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
		System.out.println(category.getParentId());
		if (StringUtils.isNoneBlank(category.getParentId())) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
			count = categoryRepository.countById(category.getParentId());

			if (count == 0) {
				log.info("Parent category with id: [{}] was not found, category: [{}]", category.getParentId(),
						category);
				throw new NotFoundException("Parent category with id = " + category.getParentId() + " was not found");
			}
		}

		// Create it
		return super.create(category);
	}

	@Override
	public List<CategoryTreeVO> listAsTree(Sort sort) {
		Assert.notNull(sort, "Sort info must not be null");

		// List all category
		List<Category> categories = listAll(sort);

		if (CollectionUtils.isEmpty(categories)) {
			return Collections.emptyList();
		}

		// Create top category
		CategoryTreeVO topLevelCategory = createTopLevelCategory();

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
	private void concreteTree(CategoryTreeVO parentCategory, List<Category> categories) {
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
			CategoryTreeVO child = new CategoryTreeVO().convertFrom(category);
			// Init children if absent
			if (parentCategory.getChildren() == null) {
				parentCategory.setChildren(new LinkedList<>());
			}

			StringBuilder fullPath = new StringBuilder();

//            if (optionService.isEnabledAbsolutePath()) {
//                fullPath.append(optionService.getBlogBaseUrl());
//            }
//
//            fullPath.append(URL_SEPARATOR)
//                    .append(optionService.getCategoriesPrefix())
//                    .append(URL_SEPARATOR)
//                    .append(child.getSlug())
//                    .append(optionService.getPathSuffix());

			child.setFullPath(fullPath.toString());

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
	private CategoryTreeVO createTopLevelCategory() {
		CategoryTreeVO topCategory = new CategoryTreeVO();
		// Set default value
		topCategory.setId("");
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
				.orElseThrow(() -> new NotFoundException("No information found in this category").setErrorData(slug));
	}

	@Override
	public Category getByName(String name) {
		return categoryRepository.getByName(name).orElse(null);
	}

	@Override
	@Transactional
	public void removeCategoryAndPostCategoryBy(String categoryId) {
		List<Category> categories = listByParentId(categoryId);
		if (null != categories && categories.size() > 0) {
			categories.forEach(category -> {
				category.setParentId("");
				update(category);
			});
		}
		// Remove category
		removeById(categoryId);
		// Remove post categories
		productCategoryService.removeByCategoryId(categoryId);
	}

	@Override
	public List<Category> listByParentId(String id) {
		Assert.notNull(id, "Parent id must not be null");
		return categoryRepository.findByParentId(id);
	}

	@Override
	public CategoryDTO convertTo(Category category) {
		Assert.notNull(category, "Category must not be null");

		CategoryDTO categoryDTO = new CategoryDTO().convertFrom(category);

		StringBuilder fullPath = new StringBuilder();

//        if (optionService.isEnabledAbsolutePath()) {
//            fullPath.append(optionService.getBlogBaseUrl());
//        }
//
//        fullPath.append(URL_SEPARATOR)
//                .append(optionService.getCategoriesPrefix())
//                .append(URL_SEPARATOR)
//                .append(category.getSlug())
//                .append(optionService.getPathSuffix());

		categoryDTO.setFullPath(fullPath.toString());

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
