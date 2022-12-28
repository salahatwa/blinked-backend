package com.blinked.modules.attachment.controllers;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

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

import com.blinked.modules.attachment.Attachment;
import com.blinked.modules.attachment.AttachmentDTO;
import com.blinked.modules.attachment.AttachmentParam;
import com.blinked.modules.attachment.AttachmentQuery;
import com.blinked.modules.attachment.AttachmentService;
import com.blinked.modules.attachment.AttachmentType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "Attachments")
@RequestMapping("/api/attachments")
@Slf4j
public class AttachmentController {

	private final AttachmentService attachmentService;

	public AttachmentController(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@GetMapping
	public Page<AttachmentDTO> pageBy(@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable,
			AttachmentQuery attachmentQuery) {
		return attachmentService.pageDtosBy(pageable, attachmentQuery);
	}

	@GetMapping("{id:\\d+}")
	@Operation(summary = "Gets attachment detail by id")
	public AttachmentDTO getBy(@PathVariable("id") Integer id) {
		Attachment attachment = attachmentService.getById(id);
		return attachmentService.convertToDto(attachment);
	}

	@PutMapping("{attachmentId:\\d+}")
	@Operation(summary = "Updates a attachment")
	public AttachmentDTO updateBy(@PathVariable("attachmentId") Integer attachmentId,
			@RequestBody @Valid AttachmentParam attachmentParam) {
		Attachment attachment = attachmentService.getById(attachmentId);
		attachmentParam.update(attachment);
		return new AttachmentDTO().convertFrom(attachmentService.update(attachment));
	}

	@DeleteMapping("{id:\\d+}")
	@Operation(summary = "Deletes attachment permanently by id")
	public AttachmentDTO deletePermanently(@PathVariable("id") Integer id) {
		return attachmentService.convertToDto(attachmentService.removePermanently(id));
	}

	@DeleteMapping
	@Operation(summary = "Deletes attachments permanently in batch by id array")
	public List<Attachment> deletePermanentlyInBatch(@RequestBody List<Integer> ids) {
		return attachmentService.removePermanently(ids);
	}

	@PostMapping("upload")
	@Operation(summary = "Uploads single file")
	public AttachmentDTO uploadAttachment(@RequestPart("file") MultipartFile file) {
		return attachmentService.convertToDto(attachmentService.upload(file));
	}

	@PostMapping(value = "uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Uploads multi files (Invalid in Swagger UI)")
	public List<AttachmentDTO> uploadAttachments(@RequestPart("files") MultipartFile[] files) {
		List<AttachmentDTO> result = new LinkedList<>();

		for (MultipartFile file : files) {
			// Upload single file
			Attachment attachment = attachmentService.upload(file);
			// Convert and add
			result.add(attachmentService.convertToDto(attachment));
		}

		return result;
	}

	@GetMapping("media_types")
	@Operation(summary = "Lists all of media types")
	public List<String> listMediaTypes() {
		return attachmentService.listAllMediaType();
	}

	@GetMapping("types")
	@Operation(summary = "Lists all of types.")
	public List<AttachmentType> listTypes() {
		return attachmentService.listAllType();
	}
}
