package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Information;

public interface InformationRepository extends JpaRepository<Information, Long>{

}
