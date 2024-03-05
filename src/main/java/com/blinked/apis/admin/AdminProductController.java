package com.blinked.apis.admin;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.util.HtmlUtils;

import com.api.common.annotations.CacheLock;
import com.api.common.exception.NotFoundException;
import com.api.common.model.BaseResponse;
import com.blinked.apis.requests.ProductContentParam;
import com.blinked.apis.requests.ProductParam;
import com.blinked.apis.requests.ProductRateParam;
import com.blinked.apis.responses.BaseRateVO;
import com.blinked.apis.responses.BaseRateWithParentVO;
import com.blinked.apis.responses.ProductDetailVO;
import com.blinked.apis.responses.ProductListVO;
import com.blinked.apis.responses.RateWithHasChildrenVO;
import com.blinked.entities.Product;
import com.blinked.entities.ProductRate;
import com.blinked.entities.dto.BaseProductDetailDTO;
import com.blinked.entities.dto.BaseProductMinimalDTO;
import com.blinked.entities.dto.BaseProductSimpleDTO;
import com.blinked.entities.dto.BaseRateDTO;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.entities.enums.RateStatus;
import com.blinked.services.ProductRateService;
import com.blinked.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Content product controller.
 *
 * @author ssatwa
 * @date 2019-04-02
 */
@RestController
@Tag(name = "Admin Products")
@RequestMapping("/api/admin/products")
public class AdminProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRateService productRateService;

	@PostMapping
	@Operation(summary = "Creates a product")
	public BaseResponse<ProductDetailVO> createBy(@Valid @RequestBody ProductParam productParam,
			@RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
		// Convert to
		Product product = productParam.convertTo();
		return BaseResponse.ok(productService.createBy(product, productParam.getTagIds(), productParam.getCategoryIds(),
				productParam.getProductMetas(), autoSave));
	}

	@PutMapping("{productId:\\d+}")
	@Operation(summary = "Updates a product")
	public BaseResponse<ProductDetailVO> updateBy(@Valid @RequestBody ProductParam productParam,
			@PathVariable("productId") Integer productId,
			@RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
		// Get the product info
		Product productToUpdate = productService.getById(productId);

		productParam.update(productToUpdate);
		return BaseResponse.ok(productService.updateBy(productToUpdate, productParam.getTagIds(),
				productParam.getCategoryIds(), productParam.getProductMetas(), autoSave));
	}

	@PutMapping("{productId:\\d+}/status/{status}")
	@Operation(summary = "Updates product status")
	public BaseResponse<BaseProductMinimalDTO> updateStatusBy(@PathVariable("productId") Integer productId,
			@PathVariable("status") ProductStatus status) {
		Product product = productService.updateStatus(status, productId);

		return BaseResponse.ok(new BaseProductMinimalDTO().convertFrom(product));
	}

	@PutMapping("status/{status}")
	@Operation(summary = "Updates product status in batch")
	public BaseResponse<List<Product>> updateStatusInBatch(@PathVariable(name = "status") ProductStatus status,
			@RequestBody List<Integer> ids) {
		return BaseResponse.ok(productService.updateStatusByIds(ids, status));
	}

	@PutMapping("{productId:\\d+}/status/draft/content")
	@Operation(summary = "Updates Product Template")
	public BaseResponse<BaseProductDetailDTO> updateTemplateBy(@PathVariable("productId") Integer productId,
			@RequestBody ProductContentParam contentParam) {
		Product product = productService.updateTemplate(contentParam.getContent(), productId);

		return BaseResponse.ok(new BaseProductDetailDTO().convertFrom(product));
	}

	@DeleteMapping("{productId:\\d+}")
	@Operation(summary = "Deletes a Product permanently")
	public void deletePermanently(@PathVariable("productId") Integer productId) {
		productService.removeById(productId);
	}

	@DeleteMapping
	@Operation(summary = "Deletes Products permanently in batch by id array")
	public BaseResponse<List<Product>> deletePermanentlyInBatch(@RequestBody List<Integer> ids) {
		return BaseResponse.ok(productService.removeByIds(ids));
	}

	@GetMapping
	@Operation(summary = "Lists products")
	public BaseResponse<Page<ProductListVO>> pageBy(
			@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
		Page<Product> productPage = productService.pageBy(ProductStatus.PUBLISHED, pageable);
		return BaseResponse.ok(productService.convertToListVo(productPage));
	}

	@GetMapping("/top_view")
	@Operation(summary = "Lists top 5 view products")
	public BaseResponse<List<ProductListVO>> topView() {
		List<Product> productPage = productService.topView();
		return BaseResponse.ok(productService.convertToListVo(productPage));
	}

	@PostMapping(value = "search")
	@Operation(summary = "Lists products by keyword")
	public BaseResponse<Page<BaseProductSimpleDTO>> pageBy(@RequestParam(value = "keyword") String keyword,
			@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
		Page<Product> productPage = productService.pageBy(keyword, pageable);
		return BaseResponse.ok(productService.convertToSimple(productPage));
	}

	@GetMapping("{productId:\\d+}")
	@Operation(summary = "Gets a product")
	public BaseResponse<ProductDetailVO> getBy(@PathVariable("productId") Integer productId,
			@RequestParam(value = "withTemplate", required = false, defaultValue = "true") Boolean withTemplate) {
		ProductDetailVO productDetailVO = productService.convertToDetailVo(productService.getById(productId));

		if (withTemplate) {
			// Clear the format content
			productDetailVO.setTemplate(null);
		}

		productService.publishVisitEvent(productDetailVO.getId());
		return BaseResponse.ok(productDetailVO);
	}

	@GetMapping("{productId:\\d+}/prev")
	@Operation(summary = "Gets previous product by current product id.")
	public BaseResponse<ProductDetailVO> getPrevProductBy(@PathVariable("productId") Integer productId) {
		Product product = productService.getById(productId);
		Product prevProduct = productService.getPrevProduct(product)
				.orElseThrow(() -> new NotFoundException("Can't find the information of this article"));

		return BaseResponse.ok(productService.convertToDetailVo(prevProduct));
	}

	@GetMapping("{productId:\\d+}/next")
	@Operation(summary = "Gets next product by current product id.")
	public BaseResponse<ProductDetailVO> getNextProductBy(@PathVariable("productId") Integer productId) {
		Product product = productService.getById(productId);
		Product nextProduct = productService.getNextProduct(product)
				.orElseThrow(() -> new NotFoundException("Can't find the information of this article"));
		return BaseResponse.ok(productService.convertToDetailVo(nextProduct));
	}

	@GetMapping("/slug")
	@Operation(summary = "Gets a product")
	public BaseResponse<ProductDetailVO> getBy(@RequestParam("slug") String slug,
			@RequestParam(value = "withTemplate", required = false, defaultValue = "true") Boolean withTemplate) {

		Product product = productService.getBySlug(slug);
		ProductDetailVO productDetailVO = productService.convertToDetailVo(product);

		Optional<Product> prevProduct = productService.getPrevProduct(product);
		Optional<Product> nextProduct = productService.getNextProduct(product);

		if (withTemplate) {
			productDetailVO.setTemplate(null);
		}

		if (prevProduct.isPresent()) {
			Product productPrev = prevProduct.get();
			productPrev.setTemplate(null);
			productDetailVO.setPrevProduct(productService.convertToDetailVo(productPrev));
		}

		if (nextProduct.isPresent()) {
			Product productNext = nextProduct.get();
			productNext.setTemplate(null);
			productDetailVO.setPrevProduct(productService.convertToDetailVo(productNext));
		}

		productService.publishVisitEvent(productDetailVO.getId());

		return BaseResponse.ok(productDetailVO);
	}

	@GetMapping("{productId:\\d+}/rates/top_view")
	public BaseResponse<Page<RateWithHasChildrenVO>> listTopRates(@PathVariable("productId") Integer productId,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@SortDefault(sort = "createTime", direction = DESC) Sort sort) {
		return BaseResponse
				.ok(productRateService.pageTopRatesBy(productId, RateStatus.PUBLISHED, PageRequest.of(page, 10, sort)));
	}

	@GetMapping("{productId:\\d+}/rates/{rateParentId}/children")
	public BaseResponse<List<BaseRateDTO>> listChildrenBy(@PathVariable("productId") Integer productId,
			@PathVariable("rateParentId") String rateParentId,
			@SortDefault(sort = "createTime", direction = DESC) Sort sort) {
		// Find all children rates
		List<ProductRate> productRates = productRateService.listChildrenBy(productId, rateParentId,
				RateStatus.PUBLISHED, sort);
		// Convert to base rate dto

		return BaseResponse.ok(productRateService.convertTo(productRates));
	}

	@GetMapping("{productId:\\d+}/rates/tree_view")
	@Operation(summary = "Lists rates with tree view")
	public BaseResponse<Page<BaseRateVO>> listRatesTree(@PathVariable("productId") Integer productId,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@SortDefault(sort = "createTime", direction = DESC) Sort sort) {
		return BaseResponse.ok(productRateService.pageVosBy(productId, PageRequest.of(page, 10, sort)));
	}

	@GetMapping("{productId:\\d+}/rates/list_view")
	@Operation(summary = "Lists rate with list view")
	public BaseResponse<Page<BaseRateWithParentVO>> listRates(@PathVariable("productId") Integer productId,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@SortDefault(sort = "createTime", direction = DESC) Sort sort) {
		return BaseResponse.ok(productRateService.pageWithParentVoBy(productId, PageRequest.of(page, 10, sort)));
	}

	@PostMapping("rates")
	@Operation(summary = "Rates a product")
	@CacheLock(autoDelete = false, traceRequest = true)
	public BaseResponse<BaseRateDTO> rate(@RequestBody ProductRateParam productRateParam) {
		productRateService.validateRateBlackListStatus();

		// Escape content
		productRateParam
				.setContent(HtmlUtils.htmlEscape(productRateParam.getContent(), StandardCharsets.UTF_8.displayName()));
		return BaseResponse.ok(productRateService.convertTo(productRateService.createBy(productRateParam)));
	}

	@PostMapping("{productId:\\d+}/likes")
	@Operation(summary = "Likes a product")
	@CacheLock(autoDelete = false, traceRequest = true)
	public void like(@PathVariable("productId") Integer productId) {
		productService.increaseLike(productId);
	}
}
