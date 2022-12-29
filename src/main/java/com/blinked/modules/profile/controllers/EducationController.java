package com.blinked.modules.profile.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.core.secuirty.CurrentUser;
import com.blinked.modules.profile.dtos.FrontEndCertificate;
import com.blinked.modules.profile.dtos.FrontEndCourse;
import com.blinked.modules.profile.entities.Certification;
import com.blinked.modules.profile.entities.Course;
import com.blinked.modules.profile.entities.Education;
import com.blinked.modules.profile.entities.Graduation;
import com.blinked.modules.profile.repositories.CertificateRepository;
import com.blinked.modules.profile.repositories.CourseRepository;
import com.blinked.modules.profile.repositories.EducationRepository;
import com.blinked.modules.profile.repositories.GraduationRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.services.CertificateService;
import com.blinked.modules.profile.services.CourseService;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Education")
@RequestMapping("/api/education-info")
public class EducationController {

	private CourseService courseService = new CourseService();

	private CertificateService certificateService = new CertificateService();

	@Autowired
	UserRepository userRepository;

	@Autowired
	EducationRepository educationRepository;

	@Autowired
	GraduationRepository graduationRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	CertificateRepository certificateRepository;

	@PostMapping("/graduation")
	@Transactional
	@Operation(summary = "Save Graduation")
	public Graduation saveGraduation(@CurrentUser Authorized authorized, @RequestBody Graduation graduation) {

		System.out.println(graduation.getType());

		graduation = graduationRepository.save(graduation);

		User user = userRepository.findById(authorized.getId()).get();
		Education education = user.getEducation();

		if (education == null) {
			education = new Education();
			education = educationRepository.save(education);
		}

		List<Graduation> graduations = education.getGraduations();

		if (graduations == null) {
			graduations = new ArrayList<Graduation>();
		}

		graduations.add(graduation);

		education.setGraduations(graduations);

		user.setEducation(education);

		userRepository.save(user);

		return graduation;
	}

	@PutMapping("/graduation")
	@Operation(summary = "Update Graduation")
	public Graduation editSaveGraduation(@RequestBody Graduation graduation) {

		graduation.setView(graduationRepository.getReferenceById(graduation.getId()).getView());

		return graduationRepository.save(graduation);
	}

	@GetMapping("/graduation/{graduationId}")
	@Operation(summary = "Get Graduation By Id")
	public Graduation getGraduation(@PathVariable("graduationId") Long graduationId) {
		return graduationRepository.getReferenceById(graduationId);
	}

	@DeleteMapping("/graduation/{graduationId}")
	@Operation(summary = "Delete Graduation")
	public void deleteGraduation(@CurrentUser Authorized authorized, @PathVariable("graduationId") Long graduationId) {
		User user = userRepository.getReferenceById(authorized.getId());
		user.getEducation().getGraduations().remove(graduationRepository.getReferenceById(graduationId));
		userRepository.save(user);
	}

	@GetMapping("/graduation/list")
	@Operation(summary = "Get Graduations List")
	public List<Graduation> getAllGraduations(@CurrentUser Authorized authorized) {

		List<Graduation> graduations = userRepository.getAllGraduations(authorized.getId());

		return graduations;
	}

	@PostMapping("/graduation/update/view/{graduationId}")
	@Operation(summary = "Change View Graduation")
	public Long changeViewGraduation(@PathVariable("graduationId") Long graduationId) {

		Graduation graduation = graduationRepository.getReferenceById(graduationId);

		if (graduation != null) {
			Boolean view = graduation.getView();

			if (view == true) {
				graduation.setView(false);
				graduation = graduationRepository.save(graduation);
				System.out.println("hello1 : " + graduation.getId());
				return graduation.getId();
			} else {
				graduation.setView(true);
				graduation = graduationRepository.save(graduation);
				System.out.println("hello2: " + graduation.getId());
				return graduation.getId();
			}

		}

		return null;

	}

	@PostMapping("/course")
	@Operation(summary = "Save Course")
	public Long saveCourse(@CurrentUser Authorized authorized, @RequestBody FrontEndCourse frontEndCourse) {

		try {

			Course course = courseService.convertFrontEndCourseToBackEndCourse(frontEndCourse, new Course());
			course = courseRepository.save(course);

			User user = userRepository.getReferenceById(authorized.getId());
			Education education = user.getEducation();

			if (education == null) {
				education = new Education();
				education = educationRepository.save(education);
			}

			List<Course> courses = education.getCourses();

			if (courses == null) {
				courses = new ArrayList<Course>();
			}

			courses.add(course);

			education.setCourses(courses);

			user.setEducation(education);

			userRepository.save(user);

			return course.getId();

		} catch (IOException e) {

			return null;
		}

	}

	@PutMapping("/course")
	@Operation(summary = "Update Course")
	public Long editSaveCourse(@RequestBody FrontEndCourse frontEndCourse) {
		Course course;
		try {
			course = courseService.convertFrontEndCourseToBackEndCourse(frontEndCourse,
					courseRepository.getReferenceById(frontEndCourse.getId()));
			course = courseRepository.save(course);
			System.out.println(course.getId());
			return course.getId();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	@GetMapping("/course/{courseId}")
	@Operation(summary = "Get Course By Id")
	public FrontEndCourse getCourse(@PathVariable("courseId") Long courseId) {
		Course course = courseRepository.getReferenceById(courseId);
		try {
			return courseService.convertBackEndCourseToFrontEndCourse(course);
		} catch (IOException | SQLException e) {
			return null;
		}
	}

	@DeleteMapping("/course/{courseId}")
	@Operation(summary = "Delete Course")
	public void deleteCourse(@CurrentUser Authorized authorized, @PathVariable("courseId") Long courseId) {
		User user = userRepository.getReferenceById(authorized.getId());
		user.getEducation().getCourses().remove(courseRepository.getReferenceById(courseId));
		userRepository.save(user);
	}

	@GetMapping("/course/list")
	@Operation(summary = "Get Course List")
	public List<FrontEndCourse> getAllCourses(@CurrentUser Authorized authorized) {

		List<Course> courses = userRepository.getAllCourses(authorized.getId());

		List<FrontEndCourse> frontEndCourses = new ArrayList<FrontEndCourse>();

		try {

			for (Course course : courses) {
				frontEndCourses.add(courseService.convertBackEndCourseToFrontEndCourse(course));
			}

			return frontEndCourses;

		} catch (IOException | SQLException e) {
			return null;
		}

	}

	@PostMapping("/course/update/view/{courseId}")
	@Operation(summary = "Change View Course")
	public Long changeViewCourse(@PathVariable("courseId") Long courseId) {

		Course course = courseRepository.getReferenceById(courseId);

		if (course != null) {
			Boolean view = course.getView();

			if (view == true) {
				course.setView(false);
				course = courseRepository.save(course);
				System.out.println("hello1 : " + course.getId());
				return course.getId();
			} else {
				course.setView(true);
				course = courseRepository.save(course);
				System.out.println("hello2: " + course.getId());
				return course.getId();
			}

		}

		return null;

	}

	@PostMapping("/certification")
	@Operation(summary = "Save Certification")
	public Long saveCertification(@CurrentUser Authorized authorized,
			@RequestBody FrontEndCertificate frontEndCertificate) {

		try {

			Certification certification = certificateService
					.convertFrontEndCertificateToBackEndCertificate(frontEndCertificate, new Certification());
			certification = certificateRepository.save(certification);

			User user = userRepository.getReferenceById(authorized.getId());
			Education education = user.getEducation();

			if (education == null) {
				education = new Education();
				education = educationRepository.save(education);
			}

			List<Certification> certifications = education.getCertification();

			if (certification == null) {
				certifications = new ArrayList<Certification>();
			}

			certifications.add(certification);

			education.setCertification(certifications);

			user.setEducation(education);

			userRepository.save(user);

			return certification.getId();

		} catch (IOException e) {
			return null;
		}

	}

	@PutMapping("/certification")
	@Operation(summary = "Update Certification")
	public Long updateCertification(@RequestBody FrontEndCertificate frontEndCertificate) {
		Certification certification;
		try {
			certification = certificateService.convertFrontEndCertificateToBackEndCertificate(frontEndCertificate,
					certificateRepository.getReferenceById(frontEndCertificate.getId()));
			certification = certificateRepository.save(certification);
			return certification.getId();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	@GetMapping("/certification/{certificationId}")
	@Operation(summary = "Get Certification By Id")
	public FrontEndCertificate getCertification(@PathVariable("certificationId") Long certificationId) {
		Certification certification = certificateRepository.getReferenceById(certificationId);
		try {
			return certificateService.convertBackEndCertificateToFrontEndCertificate(certification);
		} catch (IOException | SQLException e) {
			return null;
		}
	}

	@DeleteMapping("/certification/{certificationId}")
	@Operation(summary = "Delete Certification")
	public void deleteCertification(@CurrentUser Authorized authorized,
			@PathVariable("certificationId") Long certificationId) {
		User user = userRepository.getReferenceById(authorized.getId());
		user.getEducation().getCertification().remove(certificateRepository.getReferenceById(certificationId));
		userRepository.save(user);
	}

	@GetMapping("/certification/list")
	@Operation(summary = "Get All Certification")
	public List<FrontEndCertificate> getAllCertifications(@CurrentUser Authorized authorized) {

		List<Certification> certifications = userRepository.getAllCertifications(authorized.getId());

		List<FrontEndCertificate> frontEndCertifications = new ArrayList<FrontEndCertificate>();

		try {

			for (Certification certification : certifications) {
				frontEndCertifications
						.add(certificateService.convertBackEndCertificateToFrontEndCertificate(certification));
			}

			return frontEndCertifications;

		} catch (IOException | SQLException e) {
			return null;
		}

	}

	@PostMapping("/certification/update/view/{certificateId}")
	@Operation(summary = "Change View Certification")
	public Long changeViewCertification(@PathVariable("certificateId") Long certificateId) {

		Certification certification = certificateRepository.getReferenceById(certificateId);

		if (certification != null) {
			Boolean view = certification.getView();

			if (view == true) {
				certification.setView(false);
				certification = certificateRepository.save(certification);
				System.out.println("hello1 : " + certification.getId());
				return certification.getId();
			} else {
				certification.setView(true);
				certification = certificateRepository.save(certification);
				System.out.println("hello2: " + certification.getId());
				return certification.getId();
			}

		}

		return null;

	}

}
