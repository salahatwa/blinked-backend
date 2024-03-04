package com.blinked.services.impl;

import static com.api.common.utils.MailConstants.ERROR_SENDING_EMAIL_MESSAGE_TO;
import static com.api.common.utils.PasswordRecoveryEnvironments.MINUTES_TO_EXPIRE_RECOVERY_CODE;
import static com.api.common.utils.PasswordRecoveryEnvironments.SUBJECT_PASSWORD_RECOVERY_CODE;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.common.repo.AbstractCrudService;
import com.blinked.apis.requests.RecoveryEmailParam;
import com.blinked.entities.Recovery;
import com.blinked.entities.User;
import com.blinked.repositories.RecoveryRepository;
import com.blinked.repositories.UserRepository;
import com.blinked.services.MailService;
import com.blinked.services.RecoveryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecoveryServiceImpl extends AbstractCrudService<Recovery, Long> implements RecoveryService {

	private final UserRepository userRepository;
	private final RecoveryRepository recoveries;
	private final MailService mailService;

	public RecoveryServiceImpl(RecoveryRepository recoveries, MailService mailService, UserRepository userRepository) {
		super(recoveries);
		this.recoveries = recoveries;
		this.mailService = mailService;
		this.userRepository = userRepository;
	}

	public void recovery(String email) {

		Optional<User> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			return;
		}

		Recovery recovery = new Recovery(user.get(), MINUTES_TO_EXPIRE_RECOVERY_CODE);

		recoveries.save(recovery);

		Thread sendEmailInBackground = new Thread(() -> {
			RecoveryEmailParam recoveryEmail = new RecoveryEmailParam(email, SUBJECT_PASSWORD_RECOVERY_CODE,
					user.get().getName(), recovery.getCode());

			try {
				mailService.send(recoveryEmail);
			} catch (Exception exception) {
				log.info(ERROR_SENDING_EMAIL_MESSAGE_TO, email);
			}
		});

		sendEmailInBackground.start();
	}

	public void confirm(String email, String code) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

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
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

		Recovery recovery = recoveries
				.findFirstOptionalByUser_IdAndConfirmedIsTrueAndUsedIsFalseOrderByExpiresAtDesc(user.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

		if (!recovery.nonExpired() || !recovery.getCode().equals(code)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		user.updatePassword(password);
		userRepository.save(user);

		recovery.setUsed(true);
		recoveries.save(recovery);
	}

}
