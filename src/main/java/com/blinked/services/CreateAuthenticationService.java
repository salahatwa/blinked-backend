package com.blinked.services;

import static com.blinked.constants.MessagesConstants.CREATE_SESSION_ERROR_MESSAGE;
import static com.blinked.constants.MessagesConstants.REFRESH_SESSION_ERROR_MESSAGE;
import static com.blinked.constants.Responses.forbidden;
import static com.blinked.utils.HashIdsUtils.encode;
import static com.blinked.utils.InternationalizationUtils.message;
import static com.blinked.utils.SecurityEnvironments.JWT;
import static com.blinked.utils.SecurityEnvironments.REFRESH_TOKEN_EXPIRATION_IN_DAYS;
import static com.blinked.utils.SecurityEnvironments.TOKEN_EXPIRATION_IN_HOURS;
import static com.blinked.utils.SecurityEnvironments.TOKEN_SECRET;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blinked.apis.requests.AuthParam;
import com.blinked.apis.requests.AuthWithRefreshTokenParam;
import com.blinked.apis.responses.AuthVO;
import com.blinked.apis.responses.UserInfoVO;
import com.blinked.entities.RefreshToken;
import com.blinked.entities.User;
import com.blinked.repositories.RefreshTokenRepository;
import com.blinked.repositories.UserRepository;

@Service
public class CreateAuthenticationService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Autowired
	public CreateAuthenticationService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	public AuthVO create(AuthParam body) {
		User user = userRepository.findByEmail(body.getEmail())
				.filter(databaseUser -> databaseUser.validatePassword(body.getPassword()))
				.orElseThrow(() -> forbidden(message(CREATE_SESSION_ERROR_MESSAGE)));

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		RefreshToken refreshToken = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

		refreshTokenRepository.disableOldRefreshTokens(user.getId());

		refreshTokenRepository.save(refreshToken);

		return new AuthVO(new UserInfoVO(user), accessToken, refreshToken.getCode(), expiresAt);
	}

	public AuthVO createToken(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> forbidden(message(CREATE_SESSION_ERROR_MESSAGE)));
		
		System.out.println("///Image:"+user.getImage());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		RefreshToken refreshToken = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

		refreshTokenRepository.disableOldRefreshTokens(user.getId());

		refreshTokenRepository.save(refreshToken);

		
		System.out.println(new UserInfoVO(user).getImage());
		return new AuthVO(new UserInfoVO(user), accessToken, refreshToken.getCode(), expiresAt);
	}

	public AuthVO create(AuthWithRefreshTokenParam body) {
		RefreshToken old = refreshTokenRepository.findOptionalByCodeAndAvailableIsTrue(body.getRefreshToken())
				.filter(RefreshToken::nonExpired).orElseThrow(() -> forbidden(message(REFRESH_SESSION_ERROR_MESSAGE)));

		User user = old.getUser();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		refreshTokenRepository.disableOldRefreshTokens(user.getId());

		RefreshToken refreshToken = new RefreshToken(user, TOKEN_EXPIRATION_IN_HOURS);

		refreshTokenRepository.save(refreshToken);

		return new AuthVO(new UserInfoVO(user), accessToken, refreshToken.getCode(), expiresAt);
	}
}
