package com.blinked.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.blinked.apis.responses.ProductRateWithProductVO;
import com.blinked.apis.responses.RateWithHasChildrenVO;
import com.blinked.entities.Product;
import com.blinked.entities.ProductRate;
import com.blinked.entities.dto.BaseProductMinimalDTO;
import com.blinked.entities.enums.RateStatus;
import com.blinked.exceptions.BadRequestException;
import com.blinked.exceptions.NotFoundException;
import com.blinked.repositories.ProductRateRepository;
import com.blinked.repositories.ProductRepository;
import com.blinked.services.ProductRateService;
import com.blinked.services.UserService;
import com.blinked.utils.ServiceUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * ProductRateService implementation class
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Slf4j
@Service
public class ProductRateServiceImpl extends BaseRateServiceImpl<ProductRate> implements ProductRateService {

	private final ProductRepository productRepository;

	public ProductRateServiceImpl(ProductRateRepository productRateRepository, ProductRepository productRepository,
			UserService userService) {
		super(productRateRepository, userService);
		this.productRepository = productRepository;
	}

	@Override
	public Page<ProductRateWithProductVO> convertToWithProductVo(Page<ProductRate> commentPage) {
		Assert.notNull(commentPage, "ProductRate page must not be null");

		return new PageImpl<>(convertToWithProductVo(commentPage.getContent()), commentPage.getPageable(),
				commentPage.getTotalElements());

	}

	@Override
	public ProductRateWithProductVO convertToWithProductVo(ProductRate comment) {
		Assert.notNull(comment, "ProductRate must not be null");
		ProductRateWithProductVO productRateWithProductVO = new ProductRateWithProductVO().convertFrom(comment);

		BaseProductMinimalDTO baseProductMinimalDTO = new BaseProductMinimalDTO()
				.convertFrom(productRepository.getOne(comment.getProductId()));

		productRateWithProductVO.setProduct(baseProductMinimalDTO);
		return productRateWithProductVO;
	}

	@Override
	public List<ProductRateWithProductVO> convertToWithProductVo(List<ProductRate> productRates) {
		if (CollectionUtils.isEmpty(productRates)) {
			return Collections.emptyList();
		}

		// Fetch goods ids
		Set<Integer> productIds = ServiceUtils.fetchProperty(productRates, ProductRate::getProductId);

		// Get all products
		Map<Integer, Product> productMap = ServiceUtils.convertToMap(productRepository.findAllById(productIds),
				Product::getId);

		return productRates.stream().filter(comment -> productMap.containsKey(comment.getProductId())).map(comment -> {
			// Convert to vo
			ProductRateWithProductVO productRateWithProductVO = new ProductRateWithProductVO().convertFrom(comment);

			BaseProductMinimalDTO baseProductMinimalDTO = new BaseProductMinimalDTO()
					.convertFrom(productMap.get(comment.getProductId()));

			productRateWithProductVO.setProduct(baseProductMinimalDTO);

			return productRateWithProductVO;
		}).collect(Collectors.toList());
	}

	@Override
	public void validateTarget(Integer productId) {
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(productId));

		if (product.getDisallowRate()) {
			throw new BadRequestException("Rates on this article have been banned").setErrorData(productId);
		}
	}

	@Override
	public void validateRateBlackListStatus() {
//		RateViolationTypeEnum banStatus = commentBlackListService.commentsBanStatus(ServletUtils.getRequestIp());
//		Integer banTime = optionService.getByPropertyOrDefault(RateProperties.COMMENT_BAN_TIME, Integer.class, 10);
//		if (banStatus == RateViolationTypeEnum.FREQUENTLY) {
//			throw new ForbiddenException(
//					String.format("Your comments are too frequent, please try again in %s minutes。", banTime));
//		}
	}

	@Override
	public Page<RateWithHasChildrenVO> pageTopProductsBy(Integer targetId, RateStatus status, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
