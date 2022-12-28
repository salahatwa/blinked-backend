package com.blinked.modules.profile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "User_Website_Url")
public class UserWebsiteUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String url;

}
