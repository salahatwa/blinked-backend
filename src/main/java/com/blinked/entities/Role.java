package com.blinked.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "role")
@Where(clause = "deleted_at IS NULL")
public class Role extends AuditDate implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = true, unique = true)
	private String name;

	@Column(nullable = true, unique = true)
	private String description;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getAuthority();
	}

	@JsonIgnore
	@Override
	public String getAuthority() {
		return this.getName();
	}
}