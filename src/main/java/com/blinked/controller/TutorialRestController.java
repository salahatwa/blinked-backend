package com.blinked.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.model.User;
import com.blinked.repositories.UserRepository;

@RestController
public class TutorialRestController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/showTutorialOrNot/{userId}")
	public Boolean showTutorialOrNot(@PathVariable("userId") Long userId) {
		
		User user = userRepository.getOne(userId);
		
		if(user.getEducation()==null && user.getInformation()==null && user.getSkill()==null && user.getTemplate()==null && user.getWorkExperience()==null)
			return true;
		
		return false;
		
	}
	
}
