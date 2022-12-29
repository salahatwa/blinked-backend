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
import com.blinked.modules.profile.dtos.FrontEndProject;
import com.blinked.modules.profile.entities.Project;
import com.blinked.modules.profile.entities.Work;
import com.blinked.modules.profile.repositories.ProjectRepository;
import com.blinked.modules.profile.repositories.UserRepository;
import com.blinked.modules.profile.repositories.WorkRepository;
import com.blinked.modules.profile.services.ProjectService;
import com.blinked.modules.user.dtos.Authorized;
import com.blinked.modules.user.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Project Information")
@RequestMapping("/api/project-info")
public class ProjectController {
	private ProjectService projectService = new ProjectService();
	@Autowired
	WorkRepository workRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;

	@PostMapping
	@Operation(summary = "Save Project")
	public Long saveProject(@CurrentUser Authorized authorized, @RequestBody FrontEndProject fronEndProject) {

		try {

			Project project = projectService.FrontEndProjectToBackEndProject(fronEndProject, new Project());

			project = projectRepository.save(project);

			User user = userRepository.getReferenceById(authorized.getId());

			Work workExperience = user.getWork();

			if (workExperience == null) {
				workExperience = new Work();
				workExperience = workRepository.save(workExperience);
			}

			List<Project> projects = workExperience.getProjects();

			if (projects == null) {
				projects = new ArrayList<Project>();
			}

			projects.add(project);

			workExperience.setProjects(projects);

			user.setWork(workExperience);

			userRepository.save(user);

			return project.getId();

		} catch (IOException e) {

			return null;
		}

	}

	@PutMapping
	@Operation(summary = "Save Project")
	public Long updateProject(@RequestBody FrontEndProject frontEndProject) {

		Project project;
		try {
			project = projectService.FrontEndProjectToBackEndProject(frontEndProject,
					projectRepository.getReferenceById(frontEndProject.getId()));
			project = projectRepository.save(project);

			return project.getId();
		} catch (IOException e) {
			return null;
		}

	}

	@GetMapping("/{projectId}")
	@Operation(summary = "Get Project By Id")
	public FrontEndProject getProject(@PathVariable("projectId") Long projectId) {

		Project project = projectRepository.getReferenceById(projectId);
		try {
			return projectService.BackEndProjectToFrontEndProject(project);
		} catch (IOException | SQLException e) {
			return null;
		}

	}

	@DeleteMapping("/{projectId}")
	@Operation(summary = "Delete Project By Id")
	public void deleteProject(@CurrentUser Authorized authorized, @PathVariable("projectId") Long projectId) {
		User user = userRepository.getReferenceById(authorized.getId());
		user.getWork().getProjects().remove(projectRepository.getReferenceById(projectId));
		userRepository.save(user);
	}

	@GetMapping("/list")
	@Operation(summary = "Get Project List")
	public List<FrontEndProject> getAllProject(@CurrentUser Authorized authorized) {

		try {

			List<Project> projects = userRepository.getAllProjects(authorized.getId());
			List<FrontEndProject> frontEndProjects = new ArrayList<FrontEndProject>();

			for (Project Project : projects) {

				frontEndProjects.add(projectService.BackEndProjectToFrontEndProject(Project));

			}

			return frontEndProjects;

		} catch (IOException | SQLException e) {

			return null;
		}
	}

	@PostMapping("/update/view/{projectId}")
	@Operation(summary = "Change View Project")
	public Long changeViewProject(@PathVariable("projectId") Long projectId) {

		Project project = projectRepository.getReferenceById(projectId);

		if (project != null) {
			Boolean view = project.getView();

			if (view == true) {
				project.setView(false);
				project = projectRepository.save(project);
				System.out.println("hello1 : " + project.getId());
				return project.getId();
			} else {
				project.setView(true);
				project = projectRepository.save(project);
				System.out.println("hello2: " + project.getId());
				return project.getId();
			}

		}

		return null;

	}
}
