package com.blinked.services.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import com.blinked.entities.ProductMeta;
import com.blinked.exceptions.NotFoundException;
import com.blinked.repositories.ProductRepository;
import com.blinked.repositories.base.BaseMetaRepository;
import com.blinked.services.ProductMetaService;

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
