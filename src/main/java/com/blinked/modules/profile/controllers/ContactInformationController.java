package com.blinked.modules.profile.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.entities.ContactInformation;
import com.blinked.modules.profile.entities.Information;
import com.blinked.modules.profile.entities.SocialInformation;
import com.blinked.modules.profile.repositories.ContactInformationRepository;
import com.blinked.modules.profile.repositories.InformationRepository;
import com.blinked.modules.profile.repositories.SocialInformationRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.services.ContactInformationService;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Contact Information")
@RequestMapping("/api/user-info")
public class ContactInformationController {

	private ContactInformationService contactInformationService = new ContactInformationService();

	@Autowired
	UserRepository userRepository;

	@Autowired
	ContactInformationRepository contactInformationRepository;

	@Autowired
	InformationRepository informationRepository;

	@Autowired
	SocialInformationRepository socialInformationRepository;

	@PostMapping("/contact-info")
	@Operation(summary = "Save Contact Information")
	public ContactInformation saveContactInformation(@CurrentUser Authorized authorized,
			@RequestBody ContactInformation contactInformation) {

		System.out.println("contact: " + contactInformation.getMail());

		User user = userRepository.findById(authorized.getId()).get();

		Information userInformation = user.getInformation();

		if (userInformation == null) {
			userInformation = new Information();
			userInformation = informationRepository.save(userInformation);
		}

		ContactInformation contactInformationOfUser = contactInformationRepository.save(contactInformation);

		userInformation.setContactInformation(contactInformationOfUser);
		user.setInformation(userInformation);
		userRepository.save(user);

		return contactInformationOfUser;

	}

	@GetMapping("/contact-info")
	@Operation(summary = "Get Contact Information")
	public ContactInformation getContactInformation(@CurrentUser Authorized authorized) {

		ContactInformation contactInformationOfUser = userRepository.getContactInformation(authorized.getId());

		if (contactInformationOfUser != null)
			System.out.println(contactInformationOfUser.getMail());

		return contactInformationOfUser;
	}

	@PostMapping("/social-info")
	@Operation(summary = "Save Social Information")
	public SocialInformation saveSocialInformation(@CurrentUser Authorized authorized,
			@RequestBody SocialInformation socialInformation) {

		System.out.println("contact: " + socialInformation.getFacebookUrl());

		User user = userRepository.findById(authorized.getId()).get();

		Information userInformation = user.getInformation();

		if (userInformation == null) {
			userInformation = new Information();
			userInformation = informationRepository.save(userInformation);
		}

		SocialInformation socialInformationOfUser = socialInformationRepository.save(socialInformation);

		userInformation.setSocialInformation(socialInformationOfUser);
		user.setInformation(userInformation);
		userRepository.save(user);

		return socialInformationOfUser;

	}

	@GetMapping("/social-info")
	@Operation(summary = "Get Social Information")
	public SocialInformation getSocialInformation(@CurrentUser Authorized authorized) {

		SocialInformation socialInformationOfUser = userRepository.getSocialInformation(authorized.getId());

		if (socialInformationOfUser != null)
			System.out.println(socialInformationOfUser.getFacebookUrl());

		return socialInformationOfUser;
	}

}
