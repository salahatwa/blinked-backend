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
@Table(name = "job_internship")
public class JobInternship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "job_internshipTitle")
	private String title;

	@Column(name = "company_organizationName")
	private String company;

	private String country;

	private String state;

	private String city;

	private String startMonth;

	private String startYear;

	@Column(name = "currentlyWorkHere")
	private Boolean currentStatus;

	private String endMonth;

	private String endYear;

	@Column(name = "describeYourWork")
	private String description;

	private String attachment;

	private String typeOfAttachment;

	@Column(name = "view", nullable = false)
	private Boolean view = true;

}
