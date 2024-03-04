package com.blinked.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.api.common.exception.AlreadyExistsException;
import com.api.common.exception.NotFoundException;
import com.api.common.repo.AbstractCrudService;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.TagDTO;
import com.blinked.repositories.TagRepository;
import com.blinked.services.TagService;

import lombok.extern.slf4j.Slf4j;

/**
 * TagService implementation class.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Slf4j
@Service
public class TagServiceImpl extends AbstractCrudService<Tag, Integer> implements TagService {

	private final TagRepository tagRepository;

	public TagServiceImpl(TagRepository tagRepository) {
		super(tagRepository);
		this.tagRepository = tagRepository;
	}

	public void pageTags(Pageable pageable) {
		Page<Tag> tags = tagRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Tag create(Tag tag) {
		// Check if the tag is exist
		long count = tagRepository.countByNameOrSlug(tag.getName(), tag.getSlug());

		log.debug("Tag count: [{}]", count);

		if (count > 0) {
			// If the tag has exist already
			throw new AlreadyExistsException("The label already exists").setErrorData(tag);
		}

		// Get tag name
		return super.create(tag);
	}

	@Override
	public Tag getBySlugOfNonNull(String slug) {
		return tagRepository.getBySlug(slug)
				.orElseThrow(() -> new NotFoundException("Can't find information about this tag").setErrorData(slug));
	}

	@Override
	public Tag getBySlug(String slug) {
		return tagRepository.getBySlug(slug).orElse(null);
	}

	@Override
	public Tag getByName(String name) {
		return tagRepository.getByName(name).orElse(null);
	}

	@Override
	public TagDTO convertTo(Tag tag) {
		Assert.notNull(tag, "Tag must not be null");

		TagDTO tagDTO = new TagDTO().convertFrom(tag);

//		StringBuilder fullPath = new StringBuilder();
//
//		if (optionService.isEnabledAbsolutePath()) {
//			fullPath.append(optionService.getBlogBaseUrl());
//		}
//
//		fullPath.append(URL_SEPARATOR).append(optionService.getTagsPrefix()).append(URL_SEPARATOR).append(tag.getSlug())
//				.append(optionService.getPathSuffix());
//
//		tagDTO.setFullPath(fullPath.toString());

		return tagDTO;
	}

	@Override
	public List<TagDTO> convertTo(List<Tag> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			return Collections.emptyList();
		}

		return tags.stream().map(this::convertTo).collect(Collectors.toList());
	}
}
