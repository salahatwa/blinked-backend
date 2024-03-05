package com.blinked.services;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import com.api.common.attachment.AttachmentQuery;
import com.api.common.attachment.AttachmentType;
import com.api.common.repo.CrudService;
import com.blinked.apis.responses.AttachmentVO;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.entities.Attachment;


/**
 * Attachment service.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
public interface AttachmentService extends CrudService<Attachment, String> {

	 /**
     * Pages attachment output dtos.
     *
     * @param pageable        page info must not be null
     * @param attachmentQuery attachment query param.
     * @return a page of attachment output dto
     */
    @NonNull
    Page<AttachmentVO> pageDtosBy(AuthorizedUser user,@NonNull Pageable pageable, AttachmentQuery attachmentQuery);

    
    /**
     * Uploads file.
     *
     * @param file multipart file must not be null
     * @return attachment info
     * @throws FileOperationException throws when failed to filehandler the file
     */
    @NonNull
    Attachment upload(@NonNull MultipartFile file);

    /**
     * Removes attachment permanently.
     *
     * @param id attachment id must not be null
     * @return attachment detail deleted
     */
    @NonNull
    Attachment removePermanently(@NonNull String id);

    /**
     * Removes attachment permanently in batch.
     *
     * @param ids attachment ids must not be null
     * @return attachment detail list deleted
     */
    @NonNull
    List<Attachment> removePermanently(@NonNull Collection<String> ids);

    /**
     * Converts to attachment output dto.
     *
     * @param attachment attachment must not be null
     * @return an attachment output dto
     */
    @NonNull
    AttachmentVO convertToDto(@NonNull Attachment attachment);

    /**
     * List all media type.
     *
     * @return list of media type
     */
    List<String> listAllMediaType();

    /**
     * List all type.
     *
     * @return list of type.
     */
    List<AttachmentType> listAllType();

    /**
     * Replace attachment url in batch.
     *
     * @param oldUrl old blog url.
     * @param newUrl new blog url.
     * @return replaced attachments.
     */
    List<Attachment> replaceUrl(@NonNull String oldUrl, @NonNull String newUrl);
}
