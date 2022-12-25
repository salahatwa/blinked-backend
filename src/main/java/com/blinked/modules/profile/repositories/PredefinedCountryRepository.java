package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.PredefinedCountry;

public interface PredefinedCountryRepository extends JpaRepository<PredefinedCountry, Long>{

}
