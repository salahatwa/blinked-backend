package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PredefinedSkill {

	@Id
	private String name;     
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
