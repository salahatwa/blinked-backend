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
import com.blinked.modules.profile.dtos.FrontEndJobInternship;
import com.blinked.modules.profile.entities.JobInternship;
import com.blinked.modules.profile.entities.Work;
import com.blinked.modules.profile.repositories.JobInternshipRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.repositories.WorkRepository;
import com.blinked.modules.profile.services.JobInternshipService;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Job Internship")
@RequestMapping("/api/job-internship-info")
public class JobInternshipController {

	private JobInternshipService jobInternshipService = new JobInternshipService();

	@Autowired
	UserRepository userRepository;

	@Autowired
	WorkRepository workRepository;

	@Autowired
	JobInternshipRepository jobInternshipRepository;

	@PostMapping
	@Operation(summary = "Save JobInternship")
	public Long saveJobInternship(@CurrentUser Authorized authorized,
			@RequestBody FrontEndJobInternship frontEndJobInternship) {

		try {

			JobInternship jobInternship = jobInternshipService
					.FrontEndJobInternshipToBackEndJobInternship(frontEndJobInternship, new JobInternship());
			jobInternship = jobInternshipRepository.save(jobInternship);

			User user = userRepository.getReferenceById(authorized.getId());

			Work workExperience = user.getWork();

			if (workExperience == null) {
				workExperience = new Work();
				workExperience = workRepository.save(workExperience);
			}

			List<JobInternship> jobInternships = workExperience.getJobInternships();

			if (jobInternships == null) {
				jobInternships = new ArrayList<JobInternship>();
			}

			jobInternships.add(jobInternship);

			workExperience.setJobInternships(jobInternships);

			user.setWork(workExperience);

			userRepository.save(user);

			return jobInternship.getId();

		} catch (IOException e) {

			return null;
		}

	}

	@PutMapping
	@Operation(summary = "Update JobInternship")
	public Long editSaveJobInternship(@RequestBody FrontEndJobInternship frontEndJobInternship) {

		JobInternship jobInternship;
		try {
			jobInternship = jobInternshipService.FrontEndJobInternshipToBackEndJobInternship(frontEndJobInternship,
					jobInternshipRepository.getReferenceById(frontEndJobInternship.getId()));
			jobInternship = jobInternshipRepository.save(jobInternship);
			return jobInternship.getId();
		} catch (IOException e) {
			return null;
		}

	}

	@GetMapping("/{jobInternshipId}")
	@Operation(summary = "Get JobInternship By Id")
	public FrontEndJobInternship getJobInternship(@PathVariable("jobInternshipId") Long jobInternshipId) {

		JobInternship jobInternship = jobInternshipRepository.getReferenceById(jobInternshipId);
		try {
			return jobInternshipService.BackEndJobInternshipToFrontEndJobInternship(jobInternship);
		} catch (IOException | SQLException e) {
			return null;
		}

	}

	@DeleteMapping("/{jobInternshipId}")
	@Operation(summary = "Delete JobInternship By Id")
	public void deleteJobInternship(@PathVariable("jobInternshipId") Long jobInternshipId) {
		jobInternshipRepository.deleteById(jobInternshipId);
	}

	@GetMapping("/list")
	@Operation(summary = "Get JobInternship List")
	public List<FrontEndJobInternship> getAllJobInternship(@CurrentUser Authorized authorized) {

		try {

			List<JobInternship> jobInternships = userRepository.getAllJobInternship(authorized.getId());
			List<FrontEndJobInternship> fronEndJobInternships = new ArrayList<FrontEndJobInternship>();

			for (JobInternship jobInternship : jobInternships) {

				fronEndJobInternships
						.add(jobInternshipService.BackEndJobInternshipToFrontEndJobInternship(jobInternship));

			}

			return fronEndJobInternships;

		} catch (IOException | SQLException e) {

			return null;
		}
	}

	@PostMapping("/update/view/{jobInternshipId}")
	@Operation(summary = "Change View JobInternship")
	public Long changeViewJobInternship(@PathVariable("jobInternshipId") Long jobInternshipId) {

		JobInternship jobInternship = jobInternshipRepository.getReferenceById(jobInternshipId);

		if (jobInternship != null) {
			Boolean view = jobInternship.getView();

			if (view == true) {
				jobInternship.setView(false);
				jobInternship = jobInternshipRepository.save(jobInternship);
				System.out.println("hello1 : " + jobInternship.getId());
				return jobInternship.getId();
			} else {
				jobInternship.setView(true);
				jobInternship = jobInternshipRepository.save(jobInternship);
				System.out.println("hello2: " + jobInternship.getId());
				return jobInternship.getId();
			}

		}

		return null;

	}
}
