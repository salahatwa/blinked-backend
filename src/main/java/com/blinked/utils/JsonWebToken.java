package com.blinked.utils;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.time.ZoneId.systemDefault;
import static java.util.Arrays.stream;
import static java.util.Date.from;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.blinked.config.secuirty.AuthorizedUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JsonWebToken {
	public String encode(String id, List<String> roles, LocalDateTime expiration, String secret) {
		return Jwts.builder().setSubject(id).claim(SecurityEnvironments.ROLES_KEY_ON_JWT, String.join(",", roles))
				.setExpiration(from(expiration.atZone(systemDefault()).toInstant())).signWith(HS256, secret).compact();
	}

	public AuthorizedUser decode(String token, String secret) {
		Jws<Claims> decoded = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

		String id = decoded.getBody().getSubject();
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		String joinedRolesString = decoded.getBody().get(SecurityEnvironments.ROLES_KEY_ON_JWT).toString().trim();

		System.out.println("roles:"+joinedRolesString);
		if (nonNull(joinedRolesString) && StringUtils.isNoneBlank(joinedRolesString)) {
			String[] roles = joinedRolesString.split(",");
			authorities = stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		}

		return new AuthorizedUser(id, authorities);
	}
}
