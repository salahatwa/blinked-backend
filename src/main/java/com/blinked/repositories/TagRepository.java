package com.blinked.repositories;

import java.util.Optional;

import org.springframework.lang.NonNull;

import com.blinked.entities.Tag;
import com.blinked.repositories.base.BaseRepository;

/**
 * Tag repository.
 *
 * @author ssatwa
 */
public interface TagRepository extends BaseRepository<Tag, Integer> {

	/**
	 * Count by name or slug.
	 *
	 * @param name tag name must not be null
	 * @param slug tag slug must not be null
	 * @return tag count
	 */
	long countByNameOrSlug(@NonNull String name, @NonNull String slug);

	/**
	 * Get tag by slug
	 *
	 * @param slug slug must not be null.
	 * @return an optional of slug.
	 */
	Optional<Tag> getBySlug(@NonNull String slug);

	/**
	 * Get tag by name
	 *
	 * @param name name must not be null.
	 * @return an optional of tag
	 */
	Optional<Tag> getByName(@NonNull String name);
}
