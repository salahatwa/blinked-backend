package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
