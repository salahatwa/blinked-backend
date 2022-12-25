package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long>{

}
