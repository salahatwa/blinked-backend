package com.blinked.modules.user.services;

import static com.blinked.modules.core.utils.MailConstants.ERROR_SENDING_EMAIL_MESSAGE_TO;
import static com.blinked.modules.core.utils.PasswordRecoveryEnvironments.MINUTES_TO_EXPIRE_RECOVERY_CODE;
import static com.blinked.modules.core.utils.PasswordRecoveryEnvironments.SUBJECT_PASSWORD_RECOVERY_CODE;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.blinked.modules.user.dtos.RecoveryEmail;
import com.blinked.modules.user.entities.Recovery;
import com.blinked.modules.user.entities.User;
import com.blinked.modules.user.repositories.RecoveryRepository;
import com.blinked.modules.user.repositories.UserRepository;

@Service
public class RecoveryService {

	private static final Logger LOGGER = Logger.getLogger(RecoveryService.class.getName());

	private final UserRepository users;
	private final RecoveryRepository recoveries;
	private final MailService service;

	@Autowired
	public RecoveryService(UserRepository users, RecoveryRepository recoveries, MailService service) {
		this.users = users;
		this.recoveries = recoveries;
		this.service = service;
	}

	public void recovery(String email) {

		Optional<User> user = users.findByEmail(email);

		if (!user.isPresent()) {
			return;
		}

		Recovery recovery = new Recovery(user.get(), MINUTES_TO_EXPIRE_RECOVERY_CODE);

		recoveries.save(recovery);

		Thread sendEmailInBackground = new Thread(() -> {
			RecoveryEmail recoveryEmail = new RecoveryEmail(email, SUBJECT_PASSWORD_RECOVERY_CODE, user.get().getName(),
					recovery.getCode());

			try {
				service.send(recoveryEmail);
			} catch (Exception exception) {
				LOGGER.log(Level.INFO, ERROR_SENDING_EMAIL_MESSAGE_TO, email);
			}
		});

		sendEmailInBackground.start();
	}

	public void confirm(String email, String code) {
		User user = users.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

		Recovery recovery = recoveries
				.findFirstOptionalByUser_IdAndConfirmedIsFalseAndUsedIsFalseOrderByExpiresAtDesc(user.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

		if (!recovery.nonExpired() || !recovery.getCode().equals(code)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		recovery.setConfirmed(true);

		recoveries.save(recovery);
	}

	public void update(String email, String code, String password) {
		User user = users.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

		Recovery recovery = recoveries
				.findFirstOptionalByUser_IdAndConfirmedIsTrueAndUsedIsFalseOrderByExpiresAtDesc(user.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

		if (!recovery.nonExpired() || !recovery.getCode().equals(code)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		user.updatePassword(password);
		users.save(user);

		recovery.setUsed(true);
		recoveries.save(recovery);
	}

}
