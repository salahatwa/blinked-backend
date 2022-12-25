package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.PredefinedSkill;

public interface PredefinedSkillRespository extends JpaRepository<PredefinedSkill, String>{

}
