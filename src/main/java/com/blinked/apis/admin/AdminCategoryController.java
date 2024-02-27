package com.blinked.apis.admin;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.apis.requests.CategoryParam;
import com.blinked.apis.responses.CategoryTreeVO;
import com.blinked.entities.Category;
import com.blinked.entities.dto.CategoryDTO;
import com.blinked.services.CategoryService;
import com.blinked.services.ProductCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Category controller.
 *
 * @author ssatwa
 * @date 2024-03-21
 */
@RestController
@Tag(name = "Admin Categories")
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("{categoryId:\\d+}")
	@Operation(summary = "Gets category detail")
	public CategoryDTO getBy(@PathVariable("categoryId") Integer categoryId) {
		return categoryService.convertTo(categoryService.getById(categoryId));
	}

	@GetMapping
	@Operation(summary = "Lists all categories")
	public List<? extends CategoryDTO> listAll(@SortDefault(sort = "createTime", direction = DESC) Sort sort,
			@RequestParam(name = "more", required = false, defaultValue = "false") boolean more) {
		if (more) {
			return productCategoryService.listCategoryWithProductCountDto(sort);
		}

		return categoryService.convertTo(categoryService.listAll(sort));
	}

	@GetMapping("tree_view")
	@Operation(summary = "List all categories as tree")
	public List<CategoryTreeVO> listAsTree(@SortDefault(sort = "name", direction = ASC) Sort sort) {
		return categoryService.listAsTree(sort);
	}

	@PostMapping
	@Operation(summary = "Creates category")
	public CategoryDTO createBy(@RequestBody @Valid CategoryParam categoryParam) {
		// Convert to category
		Category category = categoryParam.convertTo();

		// Save it
		return categoryService.convertTo(categoryService.create(category));
	}

	@PutMapping("{categoryId:\\d+}")
	@Operation(summary = "Updates category")
	public CategoryDTO updateBy(@PathVariable("categoryId") Integer categoryId,
			@RequestBody @Valid CategoryParam categoryParam) {
		Category categoryToUpdate = categoryService.getById(categoryId);
		categoryParam.update(categoryToUpdate);
		return categoryService.convertTo(categoryService.update(categoryToUpdate));
	}

	@DeleteMapping("{categoryId:\\d+}")
	@Operation(summary = "Deletes category")
	public void deletePermanently(@PathVariable("categoryId") Integer categoryId) {
		categoryService.removeCategoryAndPostCategoryBy(categoryId);
	}
}
