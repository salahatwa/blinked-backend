package com.blinked.modules.user.services;

import static com.blinked.modules.core.constants.MessagesConstants.NOT_AUTHORIZED_TO_MODIFY;
import static com.blinked.modules.core.constants.MessagesConstants.NOT_AUTHORIZED_TO_READ;
import static com.blinked.modules.core.response.Responses.notFound;
import static com.blinked.modules.core.response.Responses.unauthorized;
import static com.blinked.modules.core.utils.InternationalizationUtils.message;
import static com.blinked.modules.core.validations.EmailValidations.validateEmailUniqueness;
import static com.blinked.modules.core.validations.EmailValidations.validateEmailUniquenessOnModify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blinked.modules.core.utils.Page;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.dtos.CreateUserProps;
import com.blinked.modules.user.dtos.UpdateUserProps;
import com.blinked.modules.user.entities.Role;
import com.blinked.modules.user.entities.User;
import com.blinked.modules.user.repositories.RoleRepository;
import com.blinked.modules.user.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public User find(Long id) {
		Authorized.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
				.orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_READ, "'user'")));

		return userRepository.findById(id).orElseThrow(() -> notFound("Not found"));
	}

	public Page<User> find(Optional<Integer> page, Optional<Integer> size) {
		Pageable pageable = PageRequest.of(page.get(), size.get());
		return userRepository.findAll(pageable);
	}
	
	public User create(CreateUserProps props) {
		validateEmailUniqueness(props);

		List<Role> roles = roleRepository.findOptionalByShortName("USR").map(Collections::singletonList)
				.orElseGet(Collections::emptyList);

		String name = props.getName();
		String email = props.getEmail();
		String password = props.getPassword();

		return userRepository.save(new User(name, email, password, roles));
	}
	
	public void remove(Long id) {
		Authorized.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
				.orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));

		User user = userRepository.findById(id).orElseThrow(() -> notFound("User not found"));

		userRepository.delete(user);
	}
	
	public User update(Long id, UpdateUserProps body) {
		Authorized.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
				.orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));

		User actual = userRepository.findByIdFetchRoles(id).orElseThrow(() -> notFound("User not found"));

		validateEmailUniquenessOnModify(body, actual);

		actual.merge(body);

		userRepository.save(actual);

		return actual;
	}
}
