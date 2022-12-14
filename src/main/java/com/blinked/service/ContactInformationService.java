package com.blinked.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blinked.model.ContactInformation;
import com.blinked.model.User;
import com.blinked.repositories.ContactInformationRepository;
import com.blinked.repositories.UserRepository;


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
