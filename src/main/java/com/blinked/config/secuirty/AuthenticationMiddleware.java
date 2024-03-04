package com.blinked.config.secuirty;

import static com.blinked.config.secuirty.SecurityEnvironments.TOKEN_SECRET;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@Component
@Slf4j
public class AuthenticationMiddleware extends OncePerRequestFilter {

	public static final JsonWebToken JWT = new JsonWebToken();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
			throws ServletException, IOException {

		if (!isPublicApi(request)) {

			try {
				String token = Authorization.extract(request);

//				if (isNull(token)) {
//					return;
//				}
				AuthorizedUser authorized = JWT.decode(token, TOKEN_SECRET);
				SecurityContextHolder.getContext().setAuthentication(authorized.getAuthentication());
			} catch (Exception ex) {
				log.error("Error authenticating user request : {}", ex.getMessage());
//				expired(response);
			}
		}

		filter.doFilter(request, response);
	}

	public boolean isPublicApi(HttpServletRequest request) {
		return Arrays.asList(SecurityConfiguration.API_WHITELIST).stream()
				.map(item -> (new AntPathRequestMatcher(item))).anyMatch(item -> item.matches(request));
	}
}
