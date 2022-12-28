package com.blinked.modules.profile.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.dtos.FrontEndPersonalInformation;
import com.blinked.modules.profile.entities.Information;
import com.blinked.modules.profile.entities.PersonalInformation;
import com.blinked.modules.profile.repositories.InformationRepository;
import com.blinked.modules.profile.repositories.PersonalInformationRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.services.PersonalInformationService;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Personal Information")
@RequestMapping("/api/personal-info")
public class PersonalInformationController {

	private PersonalInformationService personalInformationService = new PersonalInformationService();
	
	@Autowired
	PersonalInformationRepository personalInformationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	InformationRepository informationRepository;
	
	
	@PostMapping
	@Operation(summary = "Save Personal Information")
	public FrontEndPersonalInformation addPersonalInformation(@CurrentUser Authorized authorized,@RequestBody FrontEndPersonalInformation frontEndPersonalInformation) {
		
		try {
			PersonalInformation personalInformation = personalInformationService.convertFrontEndPersonalInfoToBackEndPersonalInfo(frontEndPersonalInformation);
			User user = userRepository.getReferenceById(authorized.getId());
			PersonalInformation personInformationOfUser = personalInformationRepository.save(personalInformation);
			Information userInformation = user.getInformation();
			
			if(userInformation==null) {
				userInformation = new Information();
				userInformation = informationRepository.save(userInformation);
			}
			
			userInformation.setPersonalInformation(personInformationOfUser);
			user.setInformation(userInformation);
			userRepository.save(user);
			
			frontEndPersonalInformation = personalInformationService.convertBackEndPersonalInfoToFrontEndPersonalInfo(personalInformation);
			
			return frontEndPersonalInformation;
			
		} catch (IOException | SQLException e) {
			return null;
		}
	}
	
	
	
	@GetMapping
	@Operation(summary = "Get Personal Information")
	public FrontEndPersonalInformation getPersonalInformation(@CurrentUser Authorized authorized) {
	
		PersonalInformation personalInformation = userRepository.getPersonalInformation(authorized.getId());
		FrontEndPersonalInformation frontEndPersonalInformation;
		
		if(personalInformation==null)
			return null;
		
		try {
			frontEndPersonalInformation = personalInformationService.convertBackEndPersonalInfoToFrontEndPersonalInfo(personalInformation);
			return frontEndPersonalInformation;
		} catch (SQLException | IOException e) {
			return null;
		}
			
		}
	
}
