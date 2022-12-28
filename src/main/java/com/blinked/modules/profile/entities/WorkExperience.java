package com.blinked.modules.profile.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workExperience")
public class WorkExperience {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "role")
	private String role;

	@Column(name = "organizationName")
	private String organization;

	private String startMonth;

	private String startYear;

	@Column(name = "currentlyWorkHere")
	private Boolean currentStatus;

	private String endMonth;

	private String endYear;

	@Column(name = "describeYourWork")
	private String description;

	@Column(name = "attachment")
	private String attachment;

	private String typeOfAttachment;

	@Column(name = "view", nullable = false)
	private Boolean view = true;
}
