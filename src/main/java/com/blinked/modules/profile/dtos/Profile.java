package com.blinked.modules.profile.dtos;

import java.util.List;

import com.blinked.modules.profile.entities.ContactInformation;
import com.blinked.modules.profile.entities.Graduation;
import com.blinked.modules.profile.entities.OtherSkill;
import com.blinked.modules.profile.entities.SocialInformation;
import com.blinked.modules.profile.entities.TechnicalSkill;
import com.blinked.modules.profile.entities.Template;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Profile {

	private Long id;

	private String name;

	private String username;

	private String password;

	private String mail;

	private Template template;

	private FrontEndPersonalInformation personalInformation;

	private ContactInformation contactInformation;

	private SocialInformation socialInformation;

	private List<Graduation> graduations;

	private List<FrontEndCourse> courses;

	private List<FrontEndCertificate> certifications;

	private List<TechnicalSkill> technicalSkills;

	private List<OtherSkill> otherSkills;

	private List<FrontEndJobInternship> jobInternships;

	private List<FrontEndExperience> volunteers;

	private List<FrontEndProject> projects;

}
