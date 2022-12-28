package com.blinked.modules.profile.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Graduation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "typeofeducation")
	private String type;

	private String universityName;

	private String country;

	private String state;

	private String city;

	@Column(name = "degree_program")
	private String degree;

	private String filedOfStudy;

	@Column(name = "averageGrade_Percentage")
	private String grade;

	@Column(name = "currentlyPersuing")
	private Boolean currentStatus;

	@Column(name = "completionYear_expectedYear")
	private String completionYear;

	@Column(name = "completionMonth_expectedMonth")
	private String completionMonth;

	// , columnDefinition = "boolean default true"
	@Column(name = "view", nullable = false)
	private Boolean view = true;

}
