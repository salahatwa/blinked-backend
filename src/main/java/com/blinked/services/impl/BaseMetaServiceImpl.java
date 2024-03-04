package com.blinked.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.api.common.repo.AbstractCrudService;
import com.api.common.utils.ServiceUtils;
import com.blinked.apis.requests.BaseMetaParam;
import com.blinked.entities.BaseMeta;
import com.blinked.entities.dto.BaseMetaDTO;
import com.blinked.repositories.BaseMetaRepository;
import com.blinked.services.BaseMetaService;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Base meta service implementation.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Slf4j
public abstract class BaseMetaServiceImpl<META extends BaseMeta> extends AbstractCrudService<META, Long>
		implements BaseMetaService<META> {

	private final BaseMetaRepository<META> baseMetaRepository;

	public BaseMetaServiceImpl(BaseMetaRepository<META> baseMetaRepository) {
		super(baseMetaRepository);
		this.baseMetaRepository = baseMetaRepository;
	}

	@Override
	@Transactional
	public List<META> createOrUpdateByProductId(@NotNull Integer postId, Set<META> metas) {
		Assert.notNull(postId, "Product id must not be null");

		// firstly remove post metas by post id
		removeByProductId(postId);

		if (CollectionUtils.isEmpty(metas)) {
			return Collections.emptyList();
		}

		// Save post metas
		metas.forEach(postMeta -> {
			if (StringUtils.isNotEmpty(postMeta.getValue()) && StringUtils.isNotEmpty(postMeta.getKey())) {
				postMeta.setProductId(postId);
				baseMetaRepository.save(postMeta);
			}
		});
		return new ArrayList<>(metas);
	}

	@Override
	public List<META> removeByProductId(@NotNull Integer postId) {
		Assert.notNull(postId, "Product id must not be null of removeByProductId");
		return baseMetaRepository.deleteByProductId(postId);
	}

	@Override
	public Map<Integer, List<META>> listProductMetaAsMap(@NotNull Set<Integer> postIds) {
		Assert.notNull(postIds, "Product ids must not be null");
		if (CollectionUtils.isEmpty(postIds)) {
			return Collections.emptyMap();
		}

		// Find all metas
		List<META> metas = baseMetaRepository.findAllByProductIdIn(postIds);

		// Convert to meta map
		Map<Long, META> postMetaMap = ServiceUtils.convertToMap(metas, META::getId);

		// Create category list map
		Map<Integer, List<META>> postMetaListMap = new HashMap<>();

		// Foreach and collect
		metas.forEach(meta -> postMetaListMap.computeIfAbsent(meta.getProductId(), postId -> new LinkedList<>())
				.add(postMetaMap.get(meta.getId())));

		return postMetaListMap;
	}

	@Override
	public @NotNull List<META> listBy(@NotNull Integer postId) {
		Assert.notNull(postId, "Product id must not be null");
		return baseMetaRepository.findAllByProductId(postId);
	}

	@Override
	public @NotNull META create(@NotNull META meta) {
		Assert.notNull(meta, "Domain must not be null");

		// Check post id
		if (!ServiceUtils.isEmptyId(meta.getProductId())) {
			validateTarget(meta.getProductId());
		}

		// Create meta
		return super.create(meta);
	}

	@Override
	public @NotNull META createBy(@NotNull BaseMetaParam<META> metaParam) {
		Assert.notNull(metaParam, "Meta param must not be null");
		return create(metaParam.convertTo());
	}

	@Override
	public Map<String, Object> convertToMap(List<META> metas) {
		return ServiceUtils.convertToMap(metas, META::getKey, META::getValue);
	}

	@Override
	public @NotNull BaseMetaDTO convertTo(@NotNull META postMeta) {
		Assert.notNull(postMeta, "Category must not be null");

		return new BaseMetaDTO().convertFrom(postMeta);
	}

	@Override
	public @NotNull List<BaseMetaDTO> convertTo(@NotNull List<META> postMetaList) {
		if (CollectionUtils.isEmpty(postMetaList)) {
			return Collections.emptyList();
		}

		return postMetaList.stream().map(this::convertTo).collect(Collectors.toList());
	}
}
