package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;

import com.blinked.modules.profile.dtos.FrontEndExperience;
import com.blinked.modules.profile.entities.WorkExperience;

public class WorkExperienceService {

	public WorkExperience FrontEndVolunteerToBackEndVolunteer(FrontEndExperience frontEndVolunteer,
			WorkExperience volunteer) throws IOException {

		volunteer.setId(frontEndVolunteer.getId());
		volunteer.setRole(frontEndVolunteer.getRole());
		volunteer.setOrganization(frontEndVolunteer.getOrganization());
		volunteer.setStartMonth(frontEndVolunteer.getStartMonth());
		volunteer.setStartYear(frontEndVolunteer.getStartYear());
		volunteer.setCurrentStatus(frontEndVolunteer.getCurrentStatus());
		volunteer.setEndMonth(frontEndVolunteer.getEndMonth());
		volunteer.setEndYear(frontEndVolunteer.getEndYear());
		volunteer.setDescription(frontEndVolunteer.getDescription());
		volunteer.setAttachment(frontEndVolunteer.getAttachment());
		volunteer.setTypeOfAttachment(frontEndVolunteer.getTypeOfAttachment());
		// volunteer.setView( frontEndVolunteer.getView() );

		return volunteer;

	}

	public FrontEndExperience BackEndVolunteerToFrontEndVolunteer(WorkExperience volunteer)
			throws IOException, SQLException {

		FrontEndExperience frontEndVolunteer = new FrontEndExperience();

		frontEndVolunteer.setId(volunteer.getId());
		frontEndVolunteer.setRole(volunteer.getRole());
		frontEndVolunteer.setOrganization(volunteer.getOrganization());
		frontEndVolunteer.setStartMonth(volunteer.getStartMonth());
		frontEndVolunteer.setStartYear(volunteer.getStartYear());
		frontEndVolunteer.setCurrentStatus(volunteer.getCurrentStatus());
		frontEndVolunteer.setEndMonth(volunteer.getEndMonth());
		frontEndVolunteer.setEndYear(volunteer.getEndYear());
		frontEndVolunteer.setDescription(volunteer.getDescription());
		frontEndVolunteer.setAttachment(volunteer.getAttachment());
		frontEndVolunteer.setTypeOfAttachment(volunteer.getTypeOfAttachment());
		frontEndVolunteer.setView(volunteer.getView());

		return frontEndVolunteer;

	}

}
