package com.blinked.entities;

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
 * Tag entity
 *
 * @author ssatwa
 * @date 2019-03-12
 */
@Data
@Entity
@Table(name = "tags", indexes = { @Index(name = "tags_name", columnList = "name") })
@ToString
@EqualsAndHashCode(callSuper = true)
public class Tag extends AuditUser {

	@Id
	@UuidGenerator
	private String id;

	/**
	 * Tag name.
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * Tag slug.
	 */
	@Column(name = "slug", unique = true)
	private String slug;

	/**
	 * Cover thumbnail of the tag.
	 */
	@Column(name = "thumbnail", length = 1023)
	private String thumbnail;
}
