package com.blinked.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.api.common.repo.BaseRepository;
import com.blinked.entities.Recovery;

public interface RecoveryRepository extends BaseRepository<Recovery, Long>, JpaSpecificationExecutor<Recovery> {

	public Optional<Recovery> findFirstOptionalByUser_IdAndConfirmedIsFalseAndUsedIsFalseOrderByExpiresAtDesc(Long id);

	public Optional<Recovery> findFirstOptionalByUser_IdAndConfirmedIsTrueAndUsedIsFalseOrderByExpiresAtDesc(Long id);
}
