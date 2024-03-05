package com.blinked.apis.authorized;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.common.attachment.AttachmentQuery;
import com.api.common.attachment.AttachmentType;
import com.api.common.model.BaseResponse;
import com.blinked.apis.requests.AttachmentParam;
import com.blinked.apis.responses.AttachmentVO;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.config.secuirty.CurrentUser;
import com.blinked.entities.Attachment;
import com.blinked.services.AttachmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "Attachments")
@RequestMapping("/api/attachments")
@Slf4j
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@GetMapping
	public BaseResponse<Page<AttachmentVO>> pageBy(@CurrentUser AuthorizedUser user,
			@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable,
			AttachmentQuery attachmentQuery) {

		return BaseResponse.ok(attachmentService.pageDtosBy(user, pageable, attachmentQuery));
	}

	@GetMapping("{id}")
	@Operation(summary = "Gets attachment detail by id")
	public BaseResponse<AttachmentVO> getBy(@PathVariable("id") String id) {
		Attachment attachment = attachmentService.getById(id);
		return BaseResponse.ok(attachmentService.convertToDto(attachment));
	}

	@PutMapping("{id}")
	@Operation(summary = "Updates a attachment")
	public BaseResponse<AttachmentVO> updateBy(@PathVariable("attachmentId") String id,
			@RequestBody @Valid AttachmentParam attachmentParam) {
		Attachment attachment = attachmentService.getById(id);
		attachmentParam.update(attachment);
		return BaseResponse.ok(new AttachmentVO().convertFrom(attachmentService.update(attachment)));
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Deletes attachment permanently by id")
	public BaseResponse<AttachmentVO> deletePermanently(@PathVariable("id") String id) {
		return BaseResponse.ok(attachmentService.convertToDto(attachmentService.removePermanently(id)));
	}

	@DeleteMapping
	@Operation(summary = "Deletes attachments permanently in batch by id array")
	public BaseResponse<List<Attachment>> deletePermanentlyInBatch(@RequestBody List<String> ids) {
		return BaseResponse.ok(attachmentService.removePermanently(ids));
	}

	@PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Uploads single file")
	public BaseResponse<AttachmentVO> uploadAttachment(@RequestPart("file") MultipartFile file) {
		return BaseResponse.ok(attachmentService.convertToDto(attachmentService.upload(file)));
	}

	@PostMapping(value = "uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Uploads multi files")
	public BaseResponse<List<AttachmentVO>> uploadAttachments(@RequestPart("files") MultipartFile[] files) {
		List<AttachmentVO> result = new LinkedList<>();

		for (MultipartFile file : files) {
			// Upload single file
			Attachment attachment = attachmentService.upload(file);
			// Convert and add
			result.add(attachmentService.convertToDto(attachment));
		}

		return BaseResponse.ok(result);
	}

	@GetMapping("media_types")
	@Operation(summary = "Lists all of media types")
	public BaseResponse<List<String>> listMediaTypes() {
		return BaseResponse.ok(attachmentService.listAllMediaType());
	}

	@GetMapping("types")
	@Operation(summary = "Lists all of types.")
	public BaseResponse<List<AttachmentType>> listTypes() {
		return BaseResponse.ok(attachmentService.listAllType());
	}
}
