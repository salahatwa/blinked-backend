package com.blinked.entities;

import org.hibernate.annotations.GenericGenerator;

import com.api.common.utils.CustomIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", type = CustomIdGenerator.class)
	private Integer id;

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
