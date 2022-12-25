package com.blinked.modules.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blinked.modules.profile.entities.PredefinedJobTitle;

public interface PredefinedJobTitleRepository extends JpaRepository<PredefinedJobTitle, String>{

}
     