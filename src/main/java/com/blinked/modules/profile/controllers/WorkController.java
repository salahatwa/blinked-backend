package com.blinked.modules.profile.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.blinked.modules.profile.dtos.FrontEndExperience;
import com.blinked.modules.profile.entities.Work;
import com.blinked.modules.profile.entities.WorkExperience;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.repositories.WorkExperienceRepository;
import com.blinked.modules.profile.repositories.WorkRepository;
import com.blinked.modules.profile.services.WorkExperienceService;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Work Experience")
@RequestMapping("/api/work-experience")
public class WorkController {

	private WorkExperienceService experienceService = new WorkExperienceService();

	@Autowired
	WorkRepository workRepository;

	@Autowired
	WorkExperienceRepository workExperienceRepository;

	@Autowired
	UserRepository userRepository;

	@PostMapping
	@Operation(summary = "Save Work Experience")
	public Long saveWorkExperience(@CurrentUser Authorized authorized,
			@RequestBody FrontEndExperience fronEndVolunteer) {

		try {

			WorkExperience experience = experienceService.FrontEndVolunteerToBackEndVolunteer(fronEndVolunteer,
					new WorkExperience());

			experience = workExperienceRepository.save(experience);

			User user = userRepository.getReferenceById(authorized.getId());

			Work workExperience = user.getWork();

			if (workExperience == null) {
				workExperience = new Work();
				workExperience = workRepository.save(workExperience);
			}

			List<WorkExperience> experiences = workExperience.getExperiences();

			if (experiences == null) {
				experiences = new ArrayList<WorkExperience>();
			}

			experiences.add(experience);

			workExperience.setExperiences(experiences);

			user.setWork(workExperience);

			userRepository.save(user);

			return experience.getId();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	@PutMapping
	@Operation(summary = "Update Work Experience")
	public Long editSaveWorkExperience(@RequestBody FrontEndExperience frontEndVolunteer) {

		WorkExperience volunteer;
		try {
			volunteer = experienceService.FrontEndVolunteerToBackEndVolunteer(frontEndVolunteer,
					workExperienceRepository.getReferenceById(frontEndVolunteer.getId()));
			volunteer = workExperienceRepository.save(volunteer);

			return volunteer.getId();
		} catch (IOException e) {
			return null;
		}

	}

	@GetMapping("/{workExperienceId}")
	@Operation(summary = "Get Work Experience By Id")
	public FrontEndExperience getWorkExperience(@PathVariable("workExperienceId") Long workExperienceId) {

		WorkExperience volunteer = workExperienceRepository.getReferenceById(workExperienceId);
		try {
			return experienceService.BackEndVolunteerToFrontEndVolunteer(volunteer);
		} catch (IOException | SQLException e) {
			return null;
		}

	}

	@DeleteMapping("/{workExperienceId}")
	@Operation(summary = "Delete Work Experience By Id")
	public void deleteWorkExperience(@CurrentUser Authorized authorized,
			@PathVariable("workExperienceId") Long workExperienceId) {
		User user = userRepository.getReferenceById(authorized.getId());
		user.getWork().getExperiences().remove(workExperienceRepository.getReferenceById(workExperienceId));
		userRepository.save(user);
	}

	@GetMapping("/list")
	@Operation(summary = "Get Work Experience List")
	public List<FrontEndExperience> getAllWorkExperiences(@CurrentUser Authorized authorized) {

		try {

			List<WorkExperience> experiences = userRepository.getAllExperiences(authorized.getId());
			List<FrontEndExperience> frontEndExperiences = new ArrayList<FrontEndExperience>();

			for (WorkExperience experience : experiences) {

				frontEndExperiences.add(experienceService.BackEndVolunteerToFrontEndVolunteer(experience));

			}

			return frontEndExperiences;

		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/update/view/{workExperienceId}")
	@Operation(summary = "Change View Work Experience")
	public Long changeViewWorkExperience(@PathVariable("workExperienceId") Long workExperienceId) {

		WorkExperience volunteer = workExperienceRepository.getReferenceById(workExperienceId);

		if (volunteer != null) {
			Boolean view = volunteer.getView();

			if (view == true) {
				volunteer.setView(false);
				volunteer = workExperienceRepository.save(volunteer);
				System.out.println("hello1 : " + volunteer.getId());
				return volunteer.getId();
			} else {
				volunteer.setView(true);
				volunteer = workExperienceRepository.save(volunteer);
				System.out.println("hello2: " + volunteer.getId());
				return volunteer.getId();
			}

		}

		return null;

	}

}
