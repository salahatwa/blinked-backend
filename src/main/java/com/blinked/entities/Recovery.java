package com.blinked.entities;

import static com.api.common.utils.Random.code;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recovery")
public class Recovery {
	@Id
	@UuidGenerator
	private String id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	@Column(name = "confirmed")
	private Boolean confirmed = false;

	@Column(name = "used")
	private Boolean used = false;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	public Recovery() {
	}

	public Recovery(User user, Integer minutesToExpire) {
		this.user = user;
		this.expiresAt = LocalDateTime.now().plusMinutes(minutesToExpire);
		this.code = code();
	}

	
	public Boolean nonExpired() {
		return expiresAt.isAfter(LocalDateTime.now());
	}
}
