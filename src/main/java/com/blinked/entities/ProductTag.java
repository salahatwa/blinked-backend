package com.blinked.entities;

import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import com.api.common.utils.CustomIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "product_tags", indexes = { @Index(name = "product_tags_product_id", columnList = "product_id"),
		@Index(name = "product_tags_tag_id", columnList = "product_id") })
public class ProductTag extends AuditUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", type = CustomIdGenerator.class)
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
