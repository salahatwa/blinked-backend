package com.blinked.modules.profile.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "technicalSkills")
public class TechnicalSkill {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String name;

	@Column(name = "view", nullable = false)
	private Boolean view = true;
}
