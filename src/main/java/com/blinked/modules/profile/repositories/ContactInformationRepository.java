package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.ContactInformation;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long>{

}
