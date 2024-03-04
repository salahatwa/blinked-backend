package com.blinked.apis.authorized;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.model.BaseResponse;
import com.blinked.apis.responses.ProductListVO;
import com.blinked.entities.Product;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.TagDTO;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.services.ProductService;
import com.blinked.services.ProductTagService;
import com.blinked.services.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * Content tag controller.
 *
 * @author ssatwa
 * @date 2019-04-02
 */
@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags")
@RequestMapping("/api/content/tags")
public class TagController {

	@Autowired
	private TagService tagService;

	@Autowired
	private ProductTagService productTagService;

	@Autowired
	private ProductService productService;

	@GetMapping
	@Operation(summary = "Lists tags")
	public BaseResponse<List<? extends TagDTO>> listTags(@SortDefault(sort = "updateTime", direction = DESC) Sort sort,
			@Parameter(description = "If the param is true, product count of tag will be returned") @RequestParam(name = "more", required = false, defaultValue = "false") Boolean more) {
		if (more) {
			return BaseResponse.ok(productTagService.listTagWithCountDtos(sort));
		}
		return BaseResponse.ok(tagService.convertTo(tagService.listAll(sort)));
	}

	@GetMapping("page")
	@Operation(summary = "Page tags")
	public BaseResponse<List<? extends TagDTO>> pageTags(
			@PageableDefault(sort = { "topPriority", "updateTime" }, direction = DESC) Pageable pageable,
			@SortDefault(sort = "updateTime", direction = DESC) Sort sort,
			@Parameter(description = "If the param is true, product count of tag will be returned") @RequestParam(name = "more", required = false, defaultValue = "false") Boolean more) {
		if (more) {
			return BaseResponse.ok(productTagService.listTagWithCountDtos(sort));
		}
		return BaseResponse.ok(tagService.convertTo(tagService.listAll(sort)));
	}

	@GetMapping("{slug}/products")
	@Operation(summary = "Lists products by tag slug")
	public BaseResponse<Page<ProductListVO>> listProductsBy(@PathVariable("slug") String slug,
			@PageableDefault(sort = { "topPriority", "updateTime" }, direction = DESC) Pageable pageable) {
		// Get tag by slug
		Tag tag = tagService.getBySlugOfNonNull(slug);

		// Get products, convert and return
		Page<Product> productPage = productTagService.pageProductsBy(tag.getId(), ProductStatus.PUBLISHED, pageable);
		return BaseResponse.ok(productService.convertToListVo(productPage));
	}
}
