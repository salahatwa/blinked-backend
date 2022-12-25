package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PredefinedJobTitle {

	@Id
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
