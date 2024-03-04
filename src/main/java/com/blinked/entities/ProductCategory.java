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
 * Post category entity.
 *
 * @author ssatwa
 */
@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@Entity
@Table(name = "product_categories", indexes = {
		@Index(name = "product_categories_product_id", columnList = "product_id"),
		@Index(name = "product_categories_category_id", columnList = "category_id") })
public class ProductCategory extends AuditUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", type = CustomIdGenerator.class)
	private Integer id;

	/**
	 * Category id.
	 */
	@Column(name = "category_id", nullable = false)
	private String categoryId;

	/**
	 * Post id.
	 */
	@Column(name = "product_id", nullable = false)
	private Integer productId;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductCategory that = (ProductCategory) o;

		return Objects.equals(categoryId, that.categoryId) && Objects.equals(productId, that.productId);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
