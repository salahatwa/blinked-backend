package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Predefined course- country- state-city - skills -job titles-field of study
 * @author ssatwa
 *
 */
@Getter
@Setter
@Table(name = "lovs")
@Entity
public class Lov {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String code;

	private String value;

	private String attribute1;

	private String attribute2;

	private String attribute3;

	private String attribute4;

}
