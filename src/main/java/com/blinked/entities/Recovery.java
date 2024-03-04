package com.blinked.entities;

import static com.api.common.utils.Random.code;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recovery")
public class Recovery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setExpiresIn(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Boolean isUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Boolean nonExpired() {
		return expiresAt.isAfter(LocalDateTime.now());
	}
}
