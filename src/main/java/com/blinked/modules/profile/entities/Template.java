package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String templateName;

	private String description;

	private String demoPath;

	private String livePath;

	private String imagePath;
}
