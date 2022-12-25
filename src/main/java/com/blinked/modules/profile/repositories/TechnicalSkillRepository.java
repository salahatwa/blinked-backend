package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.TechnicalSkill;

public interface TechnicalSkillRepository extends JpaRepository<TechnicalSkill, Long> {

}
