package com.blinked.services.impl;

import org.springframework.stereotype.Service;

import com.api.common.exception.NotFoundException;
import com.blinked.entities.ProductMeta;
import com.blinked.repositories.BaseMetaRepository;
import com.blinked.repositories.ProductRepository;
import com.blinked.services.ProductMetaService;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Product meta service implementation class.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Slf4j
@Service
public class ProductMetaServiceImpl extends BaseMetaServiceImpl<ProductMeta> implements ProductMetaService {

	private final ProductRepository postRepository;

	public ProductMetaServiceImpl(BaseMetaRepository<ProductMeta> baseMetaRepository,
			ProductRepository postRepository) {
		super(baseMetaRepository);
		this.postRepository = postRepository;
	}

	@Override
	public void validateTarget(@NotNull Integer postId) {
		postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(postId));
	}
}
