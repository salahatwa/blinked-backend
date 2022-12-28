package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "countries")
@Entity
public class PredefinedCountry {

	@Id
	private Long id;
	
	private String shortname;
	
	private String name;
	
	private String phoneCode;

	
}
