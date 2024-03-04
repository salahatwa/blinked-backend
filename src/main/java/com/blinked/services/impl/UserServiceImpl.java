package com.blinked.services.impl;

import static com.blinked.services.EmailValidations.validateEmailUniqueness;
import static com.blinked.services.EmailValidations.validateEmailUniquenessOnModify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.common.exception.BadRequestException;
import com.api.common.repo.AbstractCrudService;
import com.blinked.apis.requests.UserPropsParam;
import com.blinked.apis.requests.UserPropsUpdateParam;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.entities.Role;
import com.blinked.entities.User;
import com.blinked.repositories.RoleRepository;
import com.blinked.repositories.UserRepository;
import com.blinked.services.UserService;

@Service
public class UserServiceImpl extends AbstractCrudService<User, Long> implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super(userRepository);
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

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
				.orElseThrow(() -> new BadRequestException("Not Authorized"));

		User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

		userRepository.delete(user);
	}

	public User update(Long id, UserPropsUpdateParam body) {
		AuthorizedUser.current().filter(authorized -> authorized.itsMeOrSessionIsADM(id))
				.orElseThrow(() -> new BadRequestException("Not Authorized"));

		User actual = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

		validateEmailUniquenessOnModify(body.getEmail(), actual.getEmail());

		actual.merge(body);

		userRepository.save(actual);

		return actual;
	}
}
