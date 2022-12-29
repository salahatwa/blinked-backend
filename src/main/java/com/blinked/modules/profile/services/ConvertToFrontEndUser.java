package com.blinked.modules.profile.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blinked.modules.profile.dtos.FrontEndCertificate;
import com.blinked.modules.profile.dtos.FrontEndCourse;
import com.blinked.modules.profile.dtos.FrontEndJobInternship;
import com.blinked.modules.profile.dtos.FrontEndProject;
import com.blinked.modules.profile.dtos.FrontEndExperience;
import com.blinked.modules.profile.dtos.Profile;
import com.blinked.modules.profile.entities.Certification;
import com.blinked.modules.profile.entities.Course;
import com.blinked.modules.profile.entities.JobInternship;
import com.blinked.modules.profile.entities.Project;
import com.blinked.modules.profile.entities.WorkExperience;
import com.blinked.modules.user.entities.User;

public class ConvertToFrontEndUser {

	private PersonalInformationService personalInformationService = new PersonalInformationService();

	private JobInternshipService jobInternshipService = new JobInternshipService();

	private CourseService courseService = new CourseService();

	private CertificateService certificateService = new CertificateService();

	private WorkExperienceService volunteerService = new WorkExperienceService();

	private ProjectService projectService = new ProjectService();

	public Profile convertUserToProfile(User user) throws SQLException, IOException {

		Profile frontEndUser = new Profile();

		frontEndUser.setId(user.getId());
		frontEndUser.setName(user.getName());
		frontEndUser.setMail(user.getEmail());
		frontEndUser.setPassword(user.getPassword());
//		frontEndUser.setTemplate(user.getTemplate());

		if (user.getInformation() != null) {

			frontEndUser.setContactInformation(user.getInformation().getContactInformation());

			if (user.getInformation().getPersonalInformation() != null)
				frontEndUser.setPersonalInformation(
						personalInformationService.convertBackEndPersonalInfoToFrontEndPersonalInfo(
								user.getInformation().getPersonalInformation()));

			frontEndUser.getPersonalInformation()
					.setPicture("data:image/png;base64," + frontEndUser.getPersonalInformation().getPicture());

			frontEndUser.setSocialInformation(user.getInformation().getSocialInformation());

		}

		if (user.getEducation() != null) {
			frontEndUser.setGraduations(user.getEducation().getGraduations());

			List<FrontEndCourse> courses = new ArrayList<FrontEndCourse>();
			for (Course course : user.getEducation().getCourses()) {

				FrontEndCourse c = courseService.convertBackEndCourseToFrontEndCourse(course);

				c.setAttachment("data:application/pdf;base64," + c.getAttachment());

				courses.add(c);
			}
			frontEndUser.setCourses(courses);

			List<FrontEndCertificate> certifications = new ArrayList<FrontEndCertificate>();
			for (Certification certificate : user.getEducation().getCertification()) {

				FrontEndCertificate c = certificateService.convertBackEndCertificateToFrontEndCertificate(certificate);

				c.setAttachment("data:application/pdf;base64," + c.getAttachment());

				certifications.add(c);
			}
			frontEndUser.setCertifications(certifications);

		}

		if (user.getSkill() != null) {

			frontEndUser.setTechnicalSkills(user.getSkill().getTechnicalSkills());

			frontEndUser.setOtherSkills(user.getSkill().getOtherSkills());

		}

		if (user.getWork() != null) {
			List<FrontEndJobInternship> jobInternships = new ArrayList<FrontEndJobInternship>();
			for (JobInternship jobInternship : user.getWork().getJobInternships()) {

				FrontEndJobInternship j = jobInternshipService
						.BackEndJobInternshipToFrontEndJobInternship(jobInternship);

				j.setAttachment("data:application/pdf;base64," + j.getAttachment());

				jobInternships.add(j);

			}
			frontEndUser.setJobInternships(jobInternships);

			List<FrontEndExperience> volunteers = new ArrayList<FrontEndExperience>();
			for (WorkExperience volunteer : user.getWork().getExperiences()) {

				FrontEndExperience v = volunteerService.BackEndVolunteerToFrontEndVolunteer(volunteer);

				v.setAttachment("data:application/pdf;base64," + v.getAttachment());

				volunteers.add(v);
			}
			frontEndUser.setVolunteers(volunteers);

			List<FrontEndProject> projects = new ArrayList<FrontEndProject>();
			for (Project project : user.getWork().getProjects()) {

				FrontEndProject p = projectService.BackEndProjectToFrontEndProject(project);

				p.setAttachment("data:image/png;base64," + p.getAttachment());

				projects.add(p);
			}
			frontEndUser.setProjects(projects);

		}

		return frontEndUser;
	}

}
