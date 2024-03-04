package com.blinked.entities;

import static com.blinked.config.secuirty.SecurityEnvironments.ENCODER;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Optional.ofNullable;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Tuple;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
@Where(clause = "deleted_at IS NULL")
public class User extends AuditDate implements Serializable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "username", nullable = true)
	private String username;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "image")
	private String image;

	@JsonIgnore
	@Column(name = "deleted_email")
	private String deletedEmail;

	@JsonProperty(access = WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "active", nullable = false)
	private Boolean active = true;

	@ManyToMany(cascade = DETACH, fetch = LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@JsonIgnore
	private List<Role> roles;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String name, String email, String password, String image, List<Role> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(BigInteger id) {
		this.id = ofNullable(id).map(BigInteger::longValue).orElse(null);
	}

	public List<String> getAuthorities() {
		return ofNullable(roles).map(roles -> roles.stream().map(Role::getAuthority).collect(Collectors.toList()))
				.orElseGet(() -> new ArrayList<>());
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void merge(com.blinked.apis.requests.UserPropsUpdateParam props) {
		this.name = props.getName();
		this.email = props.getEmail();
	}

	public void updatePassword(String newPassword) {
		this.password = ENCODER.encode(newPassword);
	}

	public Boolean validatePassword(String password) {
		return ENCODER.matches(password, this.password);
	}

	@PrePersist
	private void created() {
		this.password = ENCODER.encode(password);
	}

//	public static User from(Tuple tuple) {
//		User user = new User();
//		user.setId(tuple.get("id", BigInteger.class));
//		user.setName(tuple.get("name", String.class));
//		user.setImage(tuple.get("image", String.class));
//		user.setActive(tuple.get("active", Boolean.class));
//		user.setEmail(tuple.get("email", String.class));
//		user.setPassword(tuple.get("password", String.class));
//		user.setRoles(ofNullable(tuple.get("roles", String.class))
//				.map(roles -> of(roles.split(",")).map(Role::new).collect(Collectors.toList()))
//				.orElse(new ArrayList<Role>()));
//		return user;
//	}

	public Boolean isActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}