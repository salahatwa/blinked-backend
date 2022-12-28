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
public class Certification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "certificateTitle")
	private String title;

	private String instituteName;

	@Column(name = "certificationYear")
	private String year;

	@Column(name = "certificationMonth")
	private String month;

	private String attachment;

	private String typeOfAttachment;

	private String url;

	@Column(name = "view", nullable = false)
	private Boolean view = true;

}
