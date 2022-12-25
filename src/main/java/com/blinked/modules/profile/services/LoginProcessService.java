package com.blinked.modules.profile.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.blinked.modules.profile.repositories.UserRepository;

public class LoginProcessService {

	@Autowired
	UserRepository userRepository;
	
	public boolean checkCredentials(String username, String password) {
		Long uid = new Long("0");
		userRepository.getOne(uid);
		uid = userRepository.checkCredentials(username, password);
		if(uid!=null)
			return true;
		else
			return false;
	}

	public Long getUserId(String username, String password) {
		return userRepository.getUserId(username, password);
	}

}
