package com.blinked.services;

import static com.api.common.utils.HashIdsUtils.encode;
import static com.blinked.config.secuirty.SecurityEnvironments.REFRESH_TOKEN_EXPIRATION_IN_DAYS;
import static com.blinked.config.secuirty.SecurityEnvironments.TOKEN_EXPIRATION_IN_HOURS;
import static com.blinked.config.secuirty.SecurityEnvironments.TOKEN_SECRET;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.common.exception.BadRequestException;
import com.blinked.apis.requests.AuthParam;
import com.blinked.apis.requests.AuthWithRefreshTokenParam;
import com.blinked.apis.responses.AuthVO;
import com.blinked.apis.responses.UserInfoVO;
import com.blinked.config.secuirty.AuthorizedUser;
import com.blinked.config.secuirty.JsonWebToken;
import com.blinked.entities.RefreshToken;
import com.blinked.entities.User;
import com.blinked.repositories.RefreshTokenRepository;
import com.blinked.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	public static final JsonWebToken JWT = new JsonWebToken();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Load user" + email);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		return new AuthorizedUser(user);
	}

	public AuthVO create(AuthParam body) {
		User user = userRepository.findByEmail(body.getEmail())
				.filter(databaseUser -> databaseUser.validatePassword(body.getPassword()))
				.orElseThrow(() -> new BadRequestException("You entered invalid credentials"));

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		RefreshToken refreshToken = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

		refreshTokenRepository.disableOldRefreshTokens(user);

		refreshTokenRepository.save(refreshToken);

		return new AuthVO(new UserInfoVO(user), accessToken, refreshToken.getCode(), expiresAt);
	}

	public AuthVO createToken(long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User Not found"));

		System.out.println("///Image:" + user.getImage());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		RefreshToken refreshToken = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

		refreshTokenRepository.disableOldRefreshTokens(user);

		refreshTokenRepository.save(refreshToken);

		System.out.println(new UserInfoVO(user).getImage());
		return new AuthVO(new UserInfoVO(user), accessToken, refreshToken.getCode(), expiresAt);
	}

	public AuthVO create(AuthWithRefreshTokenParam body) {
		RefreshToken old = refreshTokenRepository.findOptionalByCodeAndAvailableIsTrue(body.getRefreshToken())
				.filter(RefreshToken::nonExpired).orElseThrow(() -> new BadRequestException("Invalid token"));

		User user = old.getUser();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiresAt = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

		String accessToken = JWT.encode(encode(user.getId()), user.getAuthorities(), expiresAt, TOKEN_SECRET);

		refreshTokenRepository.disableOldRefreshTokens(user);

		RefreshToken refreshToken = new RefreshToken(user, TOKEN_EXPIRATION_IN_HOURS);

		refreshTokenRepository.save(refreshToken);

		return new AuthVO(new UserInfoVO(user), accessToken, refreshToken.getCode(), expiresAt);
	}
}
