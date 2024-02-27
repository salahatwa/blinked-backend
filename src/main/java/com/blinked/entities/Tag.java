package com.blinked.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name = "custom-id", strategy = "com.blinked.utils.CustomIdGenerator")
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
