package com.blinked.modules.user.services;

import static com.blinked.modules.core.constants.MessagesConstants.CREATE_SESSION_ERROR_MESSAGE;
import static com.blinked.modules.core.constants.MessagesConstants.REFRESH_SESSION_ERROR_MESSAGE;
import static com.blinked.modules.core.response.Responses.forbidden;
import static com.blinked.modules.core.utils.HashIdsUtils.encode;
import static com.blinked.modules.core.utils.InternationalizationUtils.message;
import static com.blinked.modules.core.utils.SecurityEnvironments.JWT;
import static com.blinked.modules.core.utils.SecurityEnvironments.REFRESH_TOKEN_EXPIRATION_IN_DAYS;
import static com.blinked.modules.core.utils.SecurityEnvironments.TOKEN_EXPIRATION_IN_HOURS;
import static com.blinked.modules.core.utils.SecurityEnvironments.TOKEN_SECRET;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blinked.modules.user.dtos.Authentication;
import com.blinked.modules.user.dtos.CreateAuthenticationWithEmailAndPassword;
import com.blinked.modules.user.dtos.CreateAuthenticationWithRefreshToken;
import com.blinked.modules.user.dtos.UserInformation;
import com.blinked.modules.user.entities.RefreshToken;
import com.blinked.modules.user.entities.User;
import com.blinked.modules.user.repositories.RefreshTokenRepository;
import com.blinked.modules.user.repositories.UserRepository;

@Service
public class CreateAuthenticationService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Autowired
	public CreateAuthenticationService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	public Authentication create(CreateAuthenticationWithEmailAndPassword body) {
		User user = userRepository.findByEmail(body.getEmail())
				.filter(databaseUser -> databaseUser.validatePassword(body.getPassword()))
				.orElseThrow(() -> forbidden(message(CREATE_SESSION_ERROR_MESSAGE)));

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		RefreshToken refreshToken = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

		refreshTokenRepository.disableOldRefreshTokens(user.getId());

		refreshTokenRepository.save(refreshToken);

		return new Authentication(new UserInformation(user), accessToken, refreshToken.getCode(), expiresAt);
	}

	public Authentication create(CreateAuthenticationWithRefreshToken body) {
		RefreshToken old = refreshTokenRepository.findOptionalByCodeAndAvailableIsTrue(body.getRefreshToken())
				.filter(RefreshToken::nonExpired).orElseThrow(() -> forbidden(message(REFRESH_SESSION_ERROR_MESSAGE)));

		User user = old.getUser();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		refreshTokenRepository.disableOldRefreshTokens(user.getId());

		RefreshToken refreshToken = new RefreshToken(user, TOKEN_EXPIRATION_IN_HOURS);

		refreshTokenRepository.save(refreshToken);

		return new Authentication(new UserInformation(user), accessToken, refreshToken.getCode(), expiresAt);
	}
}
