package com.blinked.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category entity.
 *
 * @author ssatwa
 * @date 2019-03-15
 */
@Data
@Entity
@Table(name = "categories", indexes = { @Index(name = "categories_name", columnList = "name"),
		@Index(name = "categories_parent_id", columnList = "parent_id") })
@ToString
@EqualsAndHashCode(callSuper = true)
public class Category extends AuditUser {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
//	@GenericGenerator(name = "custom-id", type = CustomIdGenerator.class)
	@UuidGenerator
	private String id;

	/**
	 * Category name.
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * Category slug.
	 */
	@Column(name = "slug", unique = true)
	private String slug;

	/**
	 * Description,can be display on category page.
	 */
	@Column(name = "description", length = 100)
	private String description;

	/**
	 * Cover thumbnail of the category.
	 */
	@Column(name = "thumbnail", length = 1023)
	private String thumbnail;

	/**
	 * Parent category.
	 */
	@Column(name = "parent_id")
	@ColumnDefault("")
	private String parentId;

	@Override
	public void prePersist() {
		super.prePersist();

		if (description == null) {
			description = "";
		}

		if (parentId == null) {
			parentId = "";
		}
	}

}
