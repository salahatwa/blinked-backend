package com.blinked.modules.core.validations;

import static com.blinked.modules.core.utils.InternationalizationUtils.message;
import static com.blinked.modules.core.utils.MessagesConstants.EMAIL_ALREADY_USED_MESSAGE;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blinked.modules.core.exceptions.BadRequestException;
import com.blinked.modules.core.response.ValidationError;
import com.blinked.modules.user.entities.Addressable;
import com.blinked.modules.user.repositories.UserRepository;

@Component
public class EmailValidations {
  private static UserRepository repository;

  @Autowired
  public EmailValidations(UserRepository repository) {
    EmailValidations.repository = repository;
  }

  public static void validateEmailUniqueness(Addressable entity) {
    if (repository.existsByEmail(entity.getEmail())) {
      throw new BadRequestException(Arrays.asList(new ValidationError("email", message(EMAIL_ALREADY_USED_MESSAGE))));
    }
  }

  public static void validateEmailUniquenessOnModify(Addressable newEntity, Addressable actualEntity) {
    String newEmail = newEntity.getEmail();
    String actualEmail = actualEntity.getEmail();

    boolean changedEmail = !actualEmail.equals(newEmail);

    boolean emailAlreadyUsed = repository.existsByEmail(newEmail);

    if (changedEmail && emailAlreadyUsed) {
      throw new BadRequestException(Arrays.asList(new ValidationError("email", message(EMAIL_ALREADY_USED_MESSAGE))));
    }
  }
}
