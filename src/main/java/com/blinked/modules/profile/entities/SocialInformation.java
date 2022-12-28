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
public class SocialInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String facebookUrl;

	private String googleUrl;

	private String instagramUrl;

	private String linkedinUrl;

	private String githubUrl;
}
