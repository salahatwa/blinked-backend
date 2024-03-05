package com.blinked.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.api.common.attachment.AttachmentType;
import com.api.common.repo.BaseRepository;
import com.blinked.entities.Attachment;

/**
 * Attachment repository
 *
 * @author ssatwa
 * @date 2019-04-03
 */
public interface AttachmentRepository
		extends BaseRepository<Attachment, String>, JpaSpecificationExecutor<Attachment> {

	/**
	 * Find all attachment media type.
	 *
	 * @return list of media type.
	 */
	@Query(value = "select distinct a.mediaType from Attachment a")
	List<String> findAllMediaType();

	/**
	 * Find all attachment type.
	 *
	 * @return list of type.
	 */
	@Query(value = "select distinct a.type from Attachment a")
	List<AttachmentType> findAllType();

	/**
	 * Counts by attachment path.
	 *
	 * @param path attachment path must not be blank
	 * @return count of the given path
	 */
	long countByPath(@NonNull String path);
}
