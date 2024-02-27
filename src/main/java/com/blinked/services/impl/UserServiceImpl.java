package com.blinked.services.impl;

import static com.blinked.constants.MessagesConstants.NOT_AUTHORIZED_TO_MODIFY;
import static com.blinked.constants.MessagesConstants.NOT_AUTHORIZED_TO_READ;
import static com.blinked.constants.Responses.notFound;
import static com.blinked.constants.Responses.unauthorized;
import static com.blinked.utils.EmailValidations.validateEmailUniqueness;
import static com.blinked.utils.EmailValidations.validateEmailUniquenessOnModify;
import static com.blinked.utils.InternationalizationUtils.message;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blinked.apis.requests.UserPropsParam;
import com.blinked.apis.requests.UserPropsUpdateParam;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.entities.Role;
import com.blinked.entities.User;
import com.blinked.repositories.RoleRepository;
import com.blinked.repositories.UserRepository;
import com.blinked.repositories.base.AbstractCrudService;
import com.blinked.services.UserService;
import com.blinked.utils.Page;

@Service
public class UserServiceImpl extends AbstractCrudService<User, Long> implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super(userRepository);
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

//	public User find(Long id) {
//		AuthorizedUser.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
//				.orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_READ, "'user'")));
//
//		return userRepository.findById(id).orElseThrow(() -> notFound("Not found"));
//	}

//	public Page<User> find(Optional<Integer> page, Optional<Integer> size) {
//		Pageable pageable = PageRequest.of(page.get(), size.get());
//		return userRepository.findAll(pageable);
//	}

	public User create(UserPropsParam props) {
		validateEmailUniqueness(props.getEmail());

		List<Role> roles = roleRepository.findOptionalByName("USR").map(Collections::singletonList)
				.orElseGet(Collections::emptyList);

		String name = props.getName();
		String email = props.getEmail();
		String password = props.getPassword();
		String image = props.getImage();

		return userRepository.save(new User(name, email, password, image, roles));
	}

	public User createForProvider(UserPropsParam props) {
		Optional<User> user = userRepository.findByEmail(props.getEmail());

		List<Role> roles = roleRepository.findOptionalByName("USR").map(Collections::singletonList)
				.orElseGet(Collections::emptyList);

		String name = props.getName();
		String email = props.getEmail();
		String password = props.getPassword();
		String image = props.getImage();

		if (user.isPresent()) {
			User localUser = user.get();
			localUser.setName(name);
			localUser.setImage(image);
			localUser.setRoles(roles);
			localUser = userRepository.save(localUser);

			return localUser;
		} else {
			return userRepository.save(new User(name, email, password, image, roles));
		}
	}

	public void remove(Long id) {
		AuthorizedUser.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
				.orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));

		User user = userRepository.findById(id).orElseThrow(() -> notFound("User not found"));

		userRepository.delete(user);
	}

	public User update(Long id, UserPropsUpdateParam body) {
		AuthorizedUser.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
				.orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));

		User actual = userRepository.findById(id).orElseThrow(() -> notFound("User not found"));

		validateEmailUniquenessOnModify(body.getEmail(), actual.getEmail());

		actual.merge(body);

		userRepository.save(actual);

		return actual;
	}
}
