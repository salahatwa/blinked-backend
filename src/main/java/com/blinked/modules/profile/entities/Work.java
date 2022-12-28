package com.blinked.modules.profile.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Work {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany
	private List<JobInternship> jobInternships;

	@OneToMany
	private List<WorkExperience> volunteers;

	@OneToMany
	private List<Project> projects;

}
