package com.blinked.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.api.common.repo.CrudService;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.TagDTO;

/**
 * Tag service interface.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
public interface TagService extends CrudService<Tag, String> {

	/**
	 * Get tag by slug
	 *
	 * @param slug slug
	 * @return Tag
	 */
	@NonNull
	Tag getBySlugOfNonNull(@NonNull String slug);

	/**
	 * Get tag by slug
	 *
	 * @param slug slug
	 * @return tag
	 */
	@NonNull
	Tag getBySlug(@NonNull String slug);

	/**
	 * Get tag by tag name.
	 *
	 * @param name name
	 * @return Tag
	 */
	@Nullable
	Tag getByName(@NonNull String name);

	/**
	 * Converts to tag dto.
	 *
	 * @param tag tag must not be null
	 * @return tag dto
	 */
	@NonNull
	TagDTO convertTo(@NonNull Tag tag);

	/**
	 * Converts to tag dtos.
	 *
	 * @param tags tag list
	 * @return a list of tag output dto
	 */
	@NonNull
	List<TagDTO> convertTo(@Nullable List<Tag> tags);
}
