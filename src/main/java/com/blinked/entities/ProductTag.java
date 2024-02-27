package com.blinked.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Product tag entity.
 *
 * @author ssatwa
 * @date 2019-03-12
 */
@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@javax.persistence.Entity
@Table(name = "product_tags", indexes = { @Index(name = "product_tags_product_id", columnList = "product_id"),
		@Index(name = "product_tags_tag_id", columnList = "product_id") })
public class ProductTag extends AuditUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", strategy = "com.blinked.utils.CustomIdGenerator")
	private Integer id;

	@Column(name = "product_id", nullable = false)
	private Integer productId;

	@Column(name = "tag_id", nullable = false)
	private Integer tagId;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductTag productTag = (ProductTag) o;
		return Objects.equals(productId, productTag.productId) && Objects.equals(tagId, productTag.tagId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, tagId);
	}
}
