package com.blinked.modules.profile.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.blinked.modules.profile.entities.ContactInformation;
import com.blinked.modules.profile.repositories.ContactInformationRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.user.entities.User;


public class ContactInformationService {

	@Autowired
	ContactInformationRepository contactInformationRepository;

	@Autowired
	UserRepository userRepository;
	
	public ContactInformation addContactInformation(ContactInformation contactInformation, Long userId) {
		
		
		User user = userRepository.getOne(userId);
		
		contactInformation.setId(new Long("0"));
		ContactInformation contactInformationOfUser = contactInformationRepository.save(contactInformation);
		
		user.getInformation().setContactInformation(contactInformationOfUser);
		
		userRepository.save(user);
		
		return contactInformationOfUser;
	}
	
	
	
}
