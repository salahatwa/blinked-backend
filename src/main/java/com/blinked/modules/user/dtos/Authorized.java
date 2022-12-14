package com.blinked.modules.user.dtos;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.blinked.modules.core.utils.HashIdsUtils;

public class Authorized extends User {

	private final Long id;
	private final String name;

	public Authorized(String id, List<SimpleGrantedAuthority> authorities) {
		super("USERNAME", "SECRET", authorities);
		this.id = HashIdsUtils.decode(id);
		this.name = "";
	}

	public Authorized(com.blinked.modules.user.entities.User user) {
		super(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, user.getRoles());
		this.id = user.getId();
		this.name = user.getEmail();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public UsernamePasswordAuthenticationToken getAuthentication() {
		return new UsernamePasswordAuthenticationToken(this, null, getAuthorities());
	}

	public Boolean isAdmin() {
		return getAuthorities().stream().anyMatch((role) -> role.getAuthority().equals("ADM"));
	}

	public Boolean itsMeOrSessionIsADM(Long id) {
		Boolean admin = isAdmin();
		boolean equals = getId().equals(id);
		if (admin) {
			return true;
		}
		return equals;
	}

	@Override
	public String toString() {
		return getId().toString();
	}

	public static Optional<Authorized> current() {
		return ofNullable(getContext().getAuthentication()).map(Authentication::getPrincipal).map((principal) -> {
			if (nonNull(principal) && principal instanceof Authorized) {
				return (Authorized) principal;
			}
			return null;
		});
	}
}