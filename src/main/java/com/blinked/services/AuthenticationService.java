package com.blinked.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.entities.User;
import com.blinked.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
	private final UserRepository repository;

	public AuthenticationService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Load user" + email);
		User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		return new AuthorizedUser(user);
	}
}
