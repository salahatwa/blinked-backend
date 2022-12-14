package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;

import com.blinked.modules.profile.dtos.FrontEndProject;
import com.blinked.modules.profile.entities.Project;

public class ProjectService {

	public Project FrontEndProjectToBackEndProject(FrontEndProject frontEndProject, Project project)
			throws IOException {

		project.setId(frontEndProject.getId());
		project.setName(frontEndProject.getName());
		project.setCompany(frontEndProject.getCompany());
		project.setStartMonth(frontEndProject.getStartMonth());
		project.setStartYear(frontEndProject.getStartYear());
		project.setCurrentStatus(frontEndProject.getCurrentStatus());
		project.setEndMonth(frontEndProject.getEndMonth());
		project.setEndYear(frontEndProject.getEndYear());
		project.setDescription(frontEndProject.getDescription());
		project.setAttachment(frontEndProject.getAttachment());
		project.setTypeOfAttachment(frontEndProject.getTypeOfAttachment());
		project.setProjectUrl(frontEndProject.getProjectUrl());
		project.setSourceUrl(frontEndProject.getSourceUrl());
		// project.setView( frontEndProject.getView() );

		return project;
	}

	public FrontEndProject BackEndProjectToFrontEndProject(Project project) throws IOException, SQLException {

		FrontEndProject frontEndProject = new FrontEndProject();

		frontEndProject.setId(project.getId());
		frontEndProject.setName(project.getName());
		frontEndProject.setCompany(project.getCompany());
		frontEndProject.setStartMonth(project.getStartMonth());
		frontEndProject.setStartYear(project.getStartYear());
		frontEndProject.setCurrentStatus(project.getCurrentStatus());
		frontEndProject.setEndMonth(project.getEndMonth());
		frontEndProject.setEndYear(project.getEndYear());
		frontEndProject.setDescription(project.getDescription());
		frontEndProject.setAttachment(project.getAttachment());
		frontEndProject.setTypeOfAttachment(project.getTypeOfAttachment());
		frontEndProject.setProjectUrl(project.getProjectUrl());
		frontEndProject.setSourceUrl(project.getSourceUrl());
		frontEndProject.setView(project.getView());

		return frontEndProject;
	}

}
