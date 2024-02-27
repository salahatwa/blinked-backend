package com.blinked.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blinked.entities.Recovery;
import com.blinked.repositories.base.BaseRepository;

public interface RecoveryRepository extends BaseRepository<Recovery, Long>, JpaSpecificationExecutor<Recovery> {
	
	public Optional<Recovery> findFirstOptionalByUser_IdAndConfirmedIsFalseAndUsedIsFalseOrderByExpiresAtDesc(Long id);

	public Optional<Recovery> findFirstOptionalByUser_IdAndConfirmedIsTrueAndUsedIsFalseOrderByExpiresAtDesc(Long id);
}
