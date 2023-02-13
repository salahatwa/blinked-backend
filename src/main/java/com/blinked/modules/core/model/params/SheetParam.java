package com.blinked.modules.core.model.params;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.blinked.modules.core.model.dto.base.InputConverter;
import com.blinked.modules.core.model.entities.Sheet;
import com.blinked.modules.core.model.entities.SheetMeta;
import com.blinked.modules.core.model.enums.PostEditorType;
import com.blinked.modules.core.model.enums.PostStatus;
import com.blinked.modules.core.utils.SlugUtils;

import lombok.Data;

/**
 * Sheet param.
 *
 * @author ssatwa
 * @date 2019-4-24
 */
@Data
public class SheetParam implements InputConverter<Sheet> {

	@NotBlank(message = "Page title cannot be empty")
	@Size(max = 100, message = "The character length of the page title cannot exceed {max}")
	private String title;

	private PostStatus status = PostStatus.DRAFT;

	@Size(max = 255, message = "The character length of the page alias cannot exceed {max}")
	private String slug;

	private PostEditorType editorType;

	private String originalContent;

	private String summary;

	@Size(max = 255, message = "The character length of the cover image link cannot exceed {max}")
	private String thumbnail;

	private Boolean disallowComment = false;

	@Size(max = 255, message = "The character length of the page password cannot exceed {max}")
	private String password;

	@Size(max = 255, message = "Length of template must not be more than {max}")
	private String template;

	@Min(value = 0, message = "Post top priority must not be less than {value}")
	private Integer topPriority = 0;

	private Date createTime;

	private String metaKeywords;

	private String metaDescription;

	private Set<SheetMetaParam> metas;

	@Override
	public Sheet convertTo() {
		slug = StringUtils.isBlank(slug) ? SlugUtils.slug(title) : SlugUtils.slug(slug);

		if (null == thumbnail) {
			thumbnail = "";
		}

		if (null == editorType) {
			editorType = PostEditorType.MARKDOWN;
		}

		return InputConverter.super.convertTo();
	}

	@Override
	public void update(Sheet sheet) {
		slug = StringUtils.isBlank(slug) ? SlugUtils.slug(title) : SlugUtils.slug(slug);

		if (null == thumbnail) {
			thumbnail = "";
		}

		if (null == editorType) {
			editorType = PostEditorType.MARKDOWN;
		}

		InputConverter.super.update(sheet);
	}

	public Set<SheetMeta> getSheetMetas() {
		Set<SheetMeta> sheetMetasSet = new HashSet<>();
		if (CollectionUtils.isEmpty(metas)) {
			return sheetMetasSet;
		}

		for (SheetMetaParam sheetMetaParam : metas) {
			SheetMeta sheetMeta = sheetMetaParam.convertTo();
			sheetMetasSet.add(sheetMeta);
		}
		return sheetMetasSet;
	}
}
