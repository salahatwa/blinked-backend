package com.blinked.modules.profile.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.profile.dtos.FrontEndPersonalInformation;
import com.blinked.modules.profile.entities.Information;
import com.blinked.modules.profile.entities.PersonalInformation;
import com.blinked.modules.profile.repositories.InformationRepository;
import com.blinked.modules.profile.repositories.PersonalInformationRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.services.PersonalInformationService;
import com.blinked.modules.user.entities.User;

@RestController
public class PersonalInformationRestController {

	private PersonalInformationService personalInformationService = new PersonalInformationService();
	
	@Autowired
	PersonalInformationRepository personalInformationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	InformationRepository informationRepository;
	
	
	@PostMapping("/addPersonalInformation/{uid}")
	public FrontEndPersonalInformation addPersonalInformation(@RequestBody FrontEndPersonalInformation frontEndPersonalInformation,
														@PathVariable("uid") Long userId) {
		
		try {
			PersonalInformation personalInformation = personalInformationService.convertFrontEndPersonalInfoToBackEndPersonalInfo(frontEndPersonalInformation);
			User user = userRepository.getOne(userId);
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
	
	
	
	@GetMapping("/getPersonalInformation/{uId}")
	public FrontEndPersonalInformation getPersonalInformation(@PathVariable("uId") Long uId) {
	
		PersonalInformation personalInformation = userRepository.getPersonalInformation(uId);
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
