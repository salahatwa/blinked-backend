package com.blinked.modules.core.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.blinked.modules.core.model.enums.OptionType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Setting entity.
 * 
 * @author ssatwa
 * @date 2019-03-20
 */
@Data
@Entity
@Table(name = "options")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Option extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
	@GenericGenerator(name = "custom-id", strategy = "com.blinked.modules.core.model.entities.CustomIdGenerator")
	private Integer id;

	/**
	 * option type
	 */
	@Column(name = "type")
	@ColumnDefault("0")
	private OptionType type;

	/**
	 * option key
	 */
	@Column(name = "option_key", length = 100, nullable = false)
	private String key;

	/**
	 * option value
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@Column(name = "option_value", nullable = false)
	private String value;

	public Option(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Option(OptionType type, String key, String value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}

	@Override
	public void prePersist() {
		super.prePersist();

		if (type == null) {
			type = OptionType.INTERNAL;
		}
	}
}
