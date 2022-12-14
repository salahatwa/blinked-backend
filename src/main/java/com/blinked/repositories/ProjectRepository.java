package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
