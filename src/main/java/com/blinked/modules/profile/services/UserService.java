package com.blinked.modules.profile.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.user.entities.User;


public class UserService {

	@Autowired
	UserRepository userRepository;

	public User addUser(User user) {
		user.setId(new Long("0"));
		return userRepository.save(user);
	}
	
}
