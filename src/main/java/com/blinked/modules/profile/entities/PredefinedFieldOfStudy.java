package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PredefinedFieldOfStudy {

	@Id
	String fieldOfStudy;

	String degree;

	public String getFieldOfStudy() {  
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	
	
}
