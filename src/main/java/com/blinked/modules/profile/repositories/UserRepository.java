package com.blinked.modules.profile.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blinked.modules.profile.entities.Certification;
import com.blinked.modules.profile.entities.ContactInformation;
import com.blinked.modules.profile.entities.Course;
import com.blinked.modules.profile.entities.Graduation;
import com.blinked.modules.profile.entities.JobInternship;
import com.blinked.modules.profile.entities.OtherSkill;
import com.blinked.modules.profile.entities.PersonalInformation;
import com.blinked.modules.profile.entities.Project;
import com.blinked.modules.profile.entities.SocialInformation;
import com.blinked.modules.profile.entities.TechnicalSkill;
import com.blinked.modules.profile.entities.Template;
import com.blinked.modules.profile.entities.UserWebsiteUrl;
import com.blinked.modules.profile.entities.Volunteer;
import com.blinked.modules.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("Select user.id from User user where user.username=?1 and user.password=?2")
	Long checkCredentials(String username, String password);

	@Query("Select user.id from User user where user.username=?1 and user.password=?2")
	Long getUserId(String username, String password);

	@Query("Select user.information.personalInformation from User user where user.id = ?1")
	PersonalInformation getPersonalInformation(Long uId);

	@Query("Select user.information.contactInformation from User user where user.id = ?1")
	ContactInformation getContactInformation(Long userId);
	
	@Query("Select user.information.socialInformation from User user where user.id = ?1")
	SocialInformation getSocialInformation(Long userId);

	@Query("Select user.skill.technicalSkills from User user where user.id = ?1")
	List<TechnicalSkill> getAllTechnicalSkills(Long uId);

	@Query("Select user.skill.otherSkills from User user where user.id = ?1")
	List<OtherSkill> getAllOtherSkills(Long uId);

	@Query("select user.education.graduations from User user where user.id=?1")
	List<Graduation> getAllGraduations(Long userId);

	@Query("select user.education.courses from User user where user.id=?1")
	List<Course> getAllCourses(Long userId);

	@Query("select user.education.certification from User user where user.id=?1")
	List<Certification> getAllCertifications(Long userId);

	@Query("select user.workExperience.jobInternships from User user where user.id=?1")
	List<JobInternship> getAllJobInternship(Long userId);

	@Query("select user.workExperience.volunteers from User user where user.id=?1")
	List<Volunteer> getAllVolunteers(Long userId);

	@Query("select user.workExperience.projects from User user where user.id=?1")
	List<Project> getAllProjects(Long userId);

	@Query("select user.template from User user where user.id=?1")
	Template getTemplateByUserId(Long uId);

	@Query("select user.websiteUrl from User user where user.id=?1")
	UserWebsiteUrl getUserWebsiteUrl(Long userId);

	@Query("select user from User user where user.websiteUrl.url=?1")
	User getUserIdFromuserUserUrl(String url);

	@Query("Select user from User user where user.username=?1")
	User checkIfUsernameExists(String username);

	@Query("Select user from User user where user.email=?1")
	User checkIfMailExists(String email);
	
	@Query("Select user.id from User user where user.email=?1")
	Long getUserIdFromMail(String email);
	
	
}
