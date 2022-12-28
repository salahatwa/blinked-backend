package com.blinked.modules.profile.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<TechnicalSkill> technicalSkills;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<OtherSkill> otherSkills;

}
