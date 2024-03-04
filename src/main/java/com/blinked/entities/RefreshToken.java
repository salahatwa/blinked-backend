package com.blinked.entities;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken implements Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String code;

	private LocalDateTime expiresAt;

	private Boolean available = true;

	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public RefreshToken(User user, Integer daysToExpire) {
		this.user = user;
		this.expiresAt = now().plusDays(daysToExpire);
		this.code = randomUUID().toString();
	}

	public Boolean nonExpired() {
		return expiresAt.isAfter(now());
	}
}
