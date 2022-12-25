package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Graduation;

public interface GraduationRepository extends JpaRepository<Graduation, Long>{

}
