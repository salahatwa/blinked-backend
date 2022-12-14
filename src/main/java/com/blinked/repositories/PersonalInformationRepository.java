package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.PersonalInformation;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long>{

}
