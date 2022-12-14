package com.blinked.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blinked.model.User;
import com.blinked.repositories.UserRepository;


public class UserService {

	@Autowired
	UserRepository userRepository;

	public User addUser(User user) {
		user.setId(new Long("0"));
		return userRepository.save(user);
	}
	
}
