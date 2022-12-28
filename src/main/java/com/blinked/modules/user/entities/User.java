package com.blinked.modules.user.entities;

import static com.blinked.modules.core.utils.SecurityEnvironments.ENCODER;
import static com.blinked.modules.user.repositories.SoftDeleteQueries.NON_DELETED_CLAUSE;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Tuple;

import org.hibernate.annotations.Where;

import com.blinked.modules.profile.entities.Education;
import com.blinked.modules.profile.entities.Information;
import com.blinked.modules.profile.entities.Skill;
import com.blinked.modules.profile.entities.Template;
import com.blinked.modules.profile.entities.UserWebsiteUrl;
import com.blinked.modules.profile.entities.Work;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
@Where(clause = NON_DELETED_CLAUSE)
public class User extends Auditable implements Serializable, Addressable {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "username", nullable = true)
	private String username;

	@Column(name = "email", unique = true)
	private String email;

	@JsonIgnore
	@Column(name = "deleted_email")
	private String deletedEmail;

	@JsonProperty(access = WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;

	
	@ManyToMany(cascade = DETACH, fetch = LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@JsonIgnore
	private List<Role> roles;
	
	
	@OneToOne
	private Template template;
	
	@OneToOne
	private Information information;
	
	@OneToOne
	private Education education;
	
	@OneToOne
	private Skill skill;
	
	@OneToOne
	private Work work;

	@OneToOne
	private UserWebsiteUrl websiteUrl;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String name, String email, String password, List<Role> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
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

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void merge(com.blinked.modules.user.dtos.UpdateUserProps props) {
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

	public static User from(Tuple tuple) {
		User user = new User();
		user.setId(tuple.get("id", BigInteger.class));
		user.setName(tuple.get("name", String.class));
		user.setActive(tuple.get("active", Boolean.class));
		user.setEmail(tuple.get("email", String.class));
		user.setPassword(tuple.get("password", String.class));
		user.setRoles(ofNullable(tuple.get("roles", String.class))
				.map(roles -> of(roles.split(",")).map(Role::new).collect(Collectors.toList()))
				.orElse(new ArrayList<Role>()));
		return user;
	}
}