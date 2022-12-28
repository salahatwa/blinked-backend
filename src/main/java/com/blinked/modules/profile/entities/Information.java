package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Information {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private ContactInformation contactInformation;

	@OneToOne
	private PersonalInformation personalInformation;

	@OneToOne
	private SocialInformation socialInformation;
}
