package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "cities")
@Entity
public class PredefinedCity {

	@Id
	private Long id;

	private String name;

	private Long state_id;

}
