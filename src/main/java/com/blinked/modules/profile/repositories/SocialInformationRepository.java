package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.SocialInformation;

public interface SocialInformationRepository extends JpaRepository<SocialInformation, Long>{

}
