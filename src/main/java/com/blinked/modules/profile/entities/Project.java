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
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String startMonth;

	private String startYear;

	@Column(name = "currentlyWorkOnThisProject")
	private Boolean currentStatus;

	private String endMonth;

	private String endYear;

	private String company;

	@Column(name = "descriptionOnProject")
	private String description;

	private String projectUrl;

	@Column(name = "repository_sourceUrl")
	private String sourceUrl;

	private String attachment;

	private String typeOfAttachment;

	@Column(name = "view", nullable = false)
	private Boolean view = true;

}
