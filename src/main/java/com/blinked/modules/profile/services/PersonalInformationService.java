package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;

import com.blinked.modules.profile.dtos.FrontEndPersonalInformation;
import com.blinked.modules.profile.entities.PersonalInformation;

public class PersonalInformationService {

	public FrontEndPersonalInformation convertBackEndPersonalInfoToFrontEndPersonalInfo(
			PersonalInformation personalInformation) throws SQLException, IOException {

		FrontEndPersonalInformation frontEndPersonalInformation = new FrontEndPersonalInformation();
		frontEndPersonalInformation.setId(personalInformation.getId());
		frontEndPersonalInformation.setName(personalInformation.getName());
		frontEndPersonalInformation.setPicture(personalInformation.getPicture());
		frontEndPersonalInformation.setSummary(personalInformation.getSummary());
		frontEndPersonalInformation.setTitle(personalInformation.getTitle());
		frontEndPersonalInformation.setTypePicture(personalInformation.getTypePicture());

		return frontEndPersonalInformation;
	}

	public PersonalInformation convertFrontEndPersonalInfoToBackEndPersonalInfo(
			FrontEndPersonalInformation frontEndPersonalInformation) throws IOException {

		PersonalInformation personalInformation = new PersonalInformation();
		personalInformation.setId(frontEndPersonalInformation.getId());
		personalInformation.setName(frontEndPersonalInformation.getName());
		personalInformation.setPicture(frontEndPersonalInformation.getPicture());
		personalInformation.setSummary(frontEndPersonalInformation.getSummary());
		personalInformation.setTitle(frontEndPersonalInformation.getTitle());
		personalInformation.setTypePicture(personalInformation.getTypePicture());

		return personalInformation;
	}

}
