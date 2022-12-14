package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long>{

}
