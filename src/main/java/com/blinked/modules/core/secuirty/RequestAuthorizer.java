package com.blinked.modules.core.secuirty;

import static com.blinked.modules.core.response.Responses.expired;
import static com.blinked.modules.core.utils.HttpContext.publicRoutes;
import static com.blinked.modules.core.utils.SecurityEnvironments.JWT;
import static com.blinked.modules.core.utils.SecurityEnvironments.TOKEN_SECRET;
import static java.util.Objects.isNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

import com.blinked.modules.core.utils.Authorization;
import com.blinked.modules.user.dtos.Authorized;

public class RequestAuthorizer {
	public static void tryAuthorizeRequest(HttpServletRequest request, HttpServletResponse response) {
		if (publicRoutes().anyMatch(request)) {
			return;
		}

		String token = Authorization.extract(request);

		if (isNull(token)) {
			return;
		}

		try {
			Authorized authorized = JWT.decode(token, TOKEN_SECRET);
			SecurityContextHolder.getContext().setAuthentication(authorized.getAuthentication());
		} catch (Exception exception) {
			expired(response);
		}
	}
}
