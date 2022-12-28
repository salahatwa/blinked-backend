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
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String instituteName;

	@Column(name = "currentlyPersuing")
	private Boolean currentStatus;

	@Column(name = "completionYear_expectedYear")
	private String completionYear;

	@Column(name = "completionMonth_expectedMonth")
	private String completionMonth;

	private String attachment;

	private String typeOfAttachment;

	private String url;

	@Column(name = "view", nullable = false)
	private Boolean view = true;

}
