package com.blinked.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.model.PredefinedCountry;

public interface PredefinedCountryRepository extends JpaRepository<PredefinedCountry, Long>{

}
