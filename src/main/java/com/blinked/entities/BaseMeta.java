package com.blinked.entities;

import org.hibernate.annotations.GenericGenerator;

import com.api.common.utils.CustomIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Data
@Entity(name = "BaseMeta")
@Table(name = "metas")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER, columnDefinition = "int default 0")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseMeta extends AuditUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", type = CustomIdGenerator.class)
	private Long id;

	
	@Column(name = "product_id", nullable = false)
	private Integer productId;

	/**
	 * meta key
	 */
	@Column(name = "meta_key", nullable = false)
	private String key;

	/**
	 * meta value
	 */
	@Column(name = "meta_value", length = 1023, nullable = false)
	private String value;
}
