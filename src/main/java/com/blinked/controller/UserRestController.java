package com.blinked.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.model.User;
import com.blinked.repositories.UserRepository;
import com.blinked.service.UserService;

@RestController
public class UserRestController {

	private UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/addUser")
	public Long addUser(@RequestBody User user) {
		user =  userRepository.save(user);
		return user.getId();
	}
	
	@PostMapping("/checkIfUsernameExists")
	public Boolean checkIfUsernameExists(@RequestBody String username) {
		
		User user = userRepository.checkIfUsernameExists(username);
		
		if(user!=null) {
			return true;
		}
		
		return false;
	}
	
}
