package com.blinked.utils;

import static com.blinked.utils.InternationalizationUtils.message;
import static com.blinked.utils.MessagesConstants.EMAIL_ALREADY_USED_MESSAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blinked.exceptions.BadRequestException;
import com.blinked.repositories.UserRepository;

@Component
public class EmailValidations {
	private static UserRepository repository;

	@Autowired
	public EmailValidations(UserRepository repository) {
		EmailValidations.repository = repository;
	}

	public static void validateEmailUniqueness(String email) {
		if (repository.existsByEmail(email)) {
			throw new BadRequestException(message(EMAIL_ALREADY_USED_MESSAGE));
		}
	}

	public static void validateEmailUniquenessOnModify(String newEmail, String actualEmail) {

		boolean changedEmail = !actualEmail.equals(newEmail);

		boolean emailAlreadyUsed = repository.existsByEmail(newEmail);

		if (changedEmail && emailAlreadyUsed) {
			throw new BadRequestException(message(EMAIL_ALREADY_USED_MESSAGE));
		}
	}
}
