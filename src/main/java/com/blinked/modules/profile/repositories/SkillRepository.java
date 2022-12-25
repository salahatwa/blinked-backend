package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long>{

}
