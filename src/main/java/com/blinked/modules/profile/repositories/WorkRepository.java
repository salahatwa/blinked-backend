package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.Work;

public interface WorkRepository extends JpaRepository<Work, Long>{

}
