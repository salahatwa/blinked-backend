package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.Information;

public interface InformationRepository extends JpaRepository<Information, Long>{

}
