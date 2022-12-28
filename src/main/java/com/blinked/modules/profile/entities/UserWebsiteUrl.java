package com.blinked.modules.profile.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "User_Website_Url", indexes = { @Index(name = "site_status", columnList = "status"),
		@Index(name = "site_url", columnList = "url") })
public class UserWebsiteUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "url", unique = true)
	private String url;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	/**
	 * Whether to top the site.
	 */
	@Column(name = "top_priority")
	@ColumnDefault("0")
	private Integer topPriority;

	@Column(name = "visits")
	@ColumnDefault("0")
	private Long visits;

	@Column(name = "status")
	@ColumnDefault("1")
	private SiteStatus status;

	@OneToOne
	@JsonIgnore
	private Template template;

}
