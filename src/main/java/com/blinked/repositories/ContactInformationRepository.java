package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.ContactInformation;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long>{

}
