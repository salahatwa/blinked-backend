package com.blinked.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.api.common.attachment.AttachmentQuery;
import com.api.common.attachment.AttachmentType;
import com.api.common.attachment.FileHandlers;
import com.api.common.attachment.UploadResult;
import com.api.common.repo.AbstractCrudService;
import com.blinked.apis.responses.AttachmentVO;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.entities.Attachment;
import com.blinked.repositories.AttachmentRepository;
import com.blinked.services.AttachmentService;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

/**
 * AttachmentService implementation
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Slf4j
@Service
public class AttachmentServiceImpl extends AbstractCrudService<Attachment, String> implements AttachmentService {

	private final AttachmentRepository attachmentRepository;

	private final FileHandlers fileHandlers;

	public AttachmentServiceImpl(AttachmentRepository attachmentRepository, FileHandlers fileHandlers) {
		super(attachmentRepository);
		this.attachmentRepository = attachmentRepository;
		this.fileHandlers = fileHandlers;
	}

	@Override
	public Page<AttachmentVO> pageDtosBy(AuthorizedUser user, @NonNull Pageable pageable,
			AttachmentQuery attachmentQuery) {
		Assert.notNull(pageable, "Page info must not be null");

		attachmentQuery.setCreatedBy(user.getId());
		// List all for specific user
		Page<Attachment> attachmentPage = attachmentRepository.findAll(buildSpecByQuery(attachmentQuery), pageable);

		// Convert and return
		return attachmentPage.map(this::convertToDto);
	}

	@NonNull
	private Specification<Attachment> buildSpecByQuery(@NonNull AttachmentQuery attachmentQuery) {
		Assert.notNull(attachmentQuery, "Attachment query must not be null");

		return (Specification<Attachment>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (attachmentQuery.getCreatedBy() != null) {
				predicates.add(criteriaBuilder.equal(root.get("createdBy"), attachmentQuery.getCreatedBy()));
			}

			if (attachmentQuery.getMediaType() != null) {
				predicates.add(criteriaBuilder.equal(root.get("mediaType"), attachmentQuery.getMediaType()));
			}

			if (attachmentQuery.getAttachmentType() != null) {
				predicates.add(criteriaBuilder.equal(root.get("type"), attachmentQuery.getAttachmentType()));
			}

			if (attachmentQuery.getKeyword() != null) {

				String likeCondition = String.format("%%%s%%", StringUtils.strip(attachmentQuery.getKeyword()));

				Predicate nameLike = criteriaBuilder.like(root.get("name"), likeCondition);

				predicates.add(criteriaBuilder.or(nameLike));
			}

			return query.where(predicates.toArray(new Predicate[0])).getRestriction();
		};
	}

	@Override
	public Attachment upload(MultipartFile file) {
		Assert.notNull(file, "Multipart file must not be null");

		AttachmentType attachmentType = getAttachmentType();

		log.info("Starting uploading... type: [{}], file: [{}]", attachmentType, file.getOriginalFilename());

		// Upload file
		UploadResult uploadResult = fileHandlers.upload(file, attachmentType);

		log.info("Attachment type: [{}]", attachmentType);
		log.info("Upload result: [{}]", uploadResult);

		// Build attachment
		Attachment attachment = new Attachment();
		attachment.setName(uploadResult.getFilename());
		// Convert separator
		attachment.setPath(uploadResult.getFilePath());
		attachment.setFileKey(uploadResult.getKey());
		attachment.setThumbPath(uploadResult.getThumbPath());
		attachment.setMediaType(uploadResult.getMediaType().toString());
		attachment.setSuffix(uploadResult.getSuffix());
		attachment.setWidth(uploadResult.getWidth());
		attachment.setHeight(uploadResult.getHeight());
		attachment.setSize(uploadResult.getSize());
		attachment.setType(attachmentType);

		log.info("Creating attachment: [{}]", attachment);

		// Create and return
		return create(attachment);
	}

	@Override
	public Attachment removePermanently(String id) {
		// Remove it from database
		Attachment deletedAttachment = removeById(id);

		// Remove the file
		fileHandlers.delete(deletedAttachment.getType(), deletedAttachment.getFileKey());

		log.debug("Deleted attachment: [{}]", deletedAttachment);

		return deletedAttachment;
	}

	@Override
	public List<Attachment> removePermanently(@Nullable Collection<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}

		return ids.stream().map(this::removePermanently).collect(Collectors.toList());
	}

	@Override
	public AttachmentVO convertToDto(Attachment attachment) {
		Assert.notNull(attachment, "Attachment must not be null");

		// Get blog base url
//		String blogBaseUrl = optionService.getBlogBaseUrl();
//
//		Boolean enabledAbsolutePath = optionService.isEnabledAbsolutePath();

		// Convert to output dto
		AttachmentVO attachmentDTO = new AttachmentVO().convertFrom(attachment);

		if (Objects.equals(attachmentDTO.getType(), AttachmentType.LOCAL)) {
			// Append blog base url to path and thumbnail
			String fullPath = attachment.getPath();
			String fullThumbPath = attachment.getThumbPath();
//
//			// Set full path and full thumb path
			attachmentDTO.setPath(fullPath);
			attachmentDTO.setThumbPath(fullThumbPath);
		}

		return attachmentDTO;
	}

	@Override
	public List<String> listAllMediaType() {
		return attachmentRepository.findAllMediaType();
	}

	@Override
	public List<AttachmentType> listAllType() {
		return attachmentRepository.findAllType();
	}

	@Override
	public List<Attachment> replaceUrl(String oldUrl, String newUrl) {
		List<Attachment> attachments = listAll();
		List<Attachment> replaced = new ArrayList<>();
		attachments.forEach(attachment -> {
			if (StringUtils.isNotEmpty(attachment.getPath())) {
				attachment.setPath(attachment.getPath().replaceAll(oldUrl, newUrl));
			}
			if (StringUtils.isNotEmpty(attachment.getThumbPath())) {
				attachment.setThumbPath(attachment.getThumbPath().replaceAll(oldUrl, newUrl));
			}
			replaced.add(attachment);
		});
		return updateInBatch(replaced);
	}

	@Override
	public Attachment create(Attachment attachment) {
		Assert.notNull(attachment, "Attachment must not be null");

		// Check attachment path
		try {
			pathMustNotExist(attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.create(attachment);
	}

	/**
	 * Attachment path must not be exist.
	 *
	 * @param attachment attachment must not be null
	 * @throws Exception
	 */
	private void pathMustNotExist(@NonNull Attachment attachment) throws Exception {
		Assert.notNull(attachment, "Attachment must not be null");

		long pathCount = attachmentRepository.countByPath(attachment.getPath());

		if (pathCount > 0) {
			throw new Exception("The attachment path is" + attachment.getPath() + " already exists");
		}
	}

	/**
	 * Get attachment type from options.
	 *
	 * @return attachment type
	 */
	@NonNull
	private AttachmentType getAttachmentType() {
		return Objects.requireNonNull(AttachmentType.LOCAL);
	}
}
