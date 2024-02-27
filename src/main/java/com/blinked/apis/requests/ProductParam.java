package com.blinked.apis.requests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.blinked.entities.Product;
import com.blinked.entities.ProductMeta;
import com.blinked.entities.enums.ProductStatus;
import com.blinked.repositories.base.InputConverter;
import com.blinked.utils.SlugUtils;

import lombok.Data;

/**
 * Product param.
 *
 * @author ssatwa
 * @date 2019-03-21
 */
@Data
public class ProductParam implements InputConverter<Product> {

	@NotBlank(message = "The title of the product cannot be empty")
	@Size(max = 200, message = "The character length of the product title cannot exceed {max}")
	private String title;

	private ProductStatus status = ProductStatus.DRAFT;

	@Size(max = 255, message = "The character length of the product alias cannot exceed {max}")
	private String slug;

	private String summary;

	@Size(max = 1023, message = "The character length of the cover image link cannot exceed {max}")
	private String thumbnail;

	private Boolean disallowRate = false;

	@Min(value = 0, message = "product price must not be less than {value}")
	private double price;

	@NotBlank(message = "The template of the product cannot be empty")
	private String template;

	@Min(value = 0, message = "Product top priority must not be less than {value}")
	private Integer topPriority = 0;

	private Date createTime;

	private String metaKeywords;

	private String metaDescription;

	private Set<Integer> tagIds;

	private Set<Integer> categoryIds;

	private Set<ProductMetaParam> metas;

	@Override
	public Product convertTo() {
		slug = StringUtils.isBlank(slug) ? SlugUtils.slug(title) : SlugUtils.slug(slug);

		if (null == thumbnail) {
			thumbnail = "";
		}

		return InputConverter.super.convertTo();
	}

	@Override
	public void update(Product product) {
		slug = StringUtils.isBlank(slug) ? SlugUtils.slug(title) : SlugUtils.slug(slug);

		if (null == thumbnail) {
			thumbnail = "";
		}

		InputConverter.super.update(product);
	}

	public Set<ProductMeta> getProductMetas() {
		Set<ProductMeta> productMetaSet = new HashSet<>();
		if (CollectionUtils.isEmpty(metas)) {
			return productMetaSet;
		}

		for (ProductMetaParam productMetaParam : metas) {
			ProductMeta productMeta = productMetaParam.convertTo();
			productMetaSet.add(productMeta);
		}
		return productMetaSet;
	}
}
