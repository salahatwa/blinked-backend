package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Education;

public interface EducationRepository extends JpaRepository<Education, Long>{

}
