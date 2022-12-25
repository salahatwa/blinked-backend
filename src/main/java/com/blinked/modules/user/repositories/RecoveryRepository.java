package com.blinked.modules.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.blinked.modules.user.entities.Recovery;

@Repository
public interface RecoveryRepository extends JpaRepository<Recovery, Long>, JpaSpecificationExecutor<Recovery> {
    public Optional<Recovery> findFirstOptionalByUser_IdAndConfirmedIsFalseAndUsedIsFalseOrderByExpiresAtDesc(Long id);
    public Optional<Recovery> findFirstOptionalByUser_IdAndConfirmedIsTrueAndUsedIsFalseOrderByExpiresAtDesc(Long id);
}
