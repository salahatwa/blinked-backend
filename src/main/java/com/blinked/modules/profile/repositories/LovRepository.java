package com.blinked.modules.profile.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blinked.modules.profile.entities.Lov;

public interface LovRepository extends JpaRepository<Lov, Long> {

	@Query("Select lov from Lov lov where lov.code=?1")
	List<Lov> getLovByCode(String lovCode);

}
