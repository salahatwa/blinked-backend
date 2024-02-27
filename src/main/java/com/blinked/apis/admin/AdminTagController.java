package com.blinked.apis.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.apis.requests.TagParam;
import com.blinked.entities.Tag;
import com.blinked.entities.dto.TagDTO;
import com.blinked.services.ProductTagService;
import com.blinked.services.TagService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

/**
 * Tag controller.
 *
 * @author ssatwa
 * @date 3/20/19
 */
@Slf4j
@io.swagger.v3.oas.annotations.tags.Tag(name = "Admin Tags")
@RestController
@RequestMapping("/api/admin/tags")
public class AdminTagController {

	@Autowired
	private TagService tagService;

	@Autowired
	private ProductTagService postTagService;

	@GetMapping
	@Operation(summary = "Lists tags")
	public List<? extends TagDTO> listTags(@SortDefault(sort = "createTime", direction = Sort.Direction.DESC) Sort sort,
			@Param("Return more information(post count) if it is set") @RequestParam(name = "more", required = false, defaultValue = "false") Boolean more) {
		if (more) {
			return postTagService.listTagWithCountDtos(sort);
		}
		return tagService.convertTo(tagService.listAll(sort));
	}

	@PostMapping
	@Operation(summary = "Creates a tag")
	public TagDTO createTag(@Valid @RequestBody TagParam tagParam) {
		// Convert to tag
		Tag tag = tagParam.convertTo();

		log.debug("Tag to be created: [{}]", tag);

		// Create and convert
		return tagService.convertTo(tagService.create(tag));
	}

	@GetMapping("{tagId:\\d+}")
	@Operation(summary = "Gets tag detail by id")
	public TagDTO getBy(@PathVariable("tagId") Integer tagId) {
		return tagService.convertTo(tagService.getById(tagId));
	}

	@PutMapping("{tagId:\\d+}")
	@Operation(summary = "Updates a tag")
	public TagDTO updateBy(@PathVariable("tagId") Integer tagId, @Valid @RequestBody TagParam tagParam) {
		// Get old tag
		Tag tag = tagService.getById(tagId);

		// Update tag
		tagParam.update(tag);

		// Update tag
		return tagService.convertTo(tagService.update(tag));
	}

	@DeleteMapping("{tagId:\\d+}")
	@Operation(summary = "Deletes a tag")
	public TagDTO deletePermanently(@PathVariable("tagId") Integer tagId) {
		// Remove the tag
		Tag deletedTag = tagService.removeById(tagId);
		// Remove the post tag relationship
		postTagService.removeByTagId(tagId);

		return tagService.convertTo(deletedTag);
	}
}
