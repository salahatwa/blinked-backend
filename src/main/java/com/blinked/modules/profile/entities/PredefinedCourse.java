package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PredefinedCourse {

	@Id
	String name;

}
