package com.blinked.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.blinked.entities.RefreshToken;
import com.blinked.repositories.base.BaseRepository;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
	@Transactional
	@Modifying
	@Query("UPDATE RefreshToken SET available = false WHERE user_id = ?1 AND available = true")
	public void disableOldRefreshTokens(Long id);

	@Query("SELECT refresh FROM RefreshToken refresh JOIN FETCH refresh.user user JOIN FETCH user.roles WHERE refresh.code = ?1 AND refresh.available = true")
	public Optional<RefreshToken> findOptionalByCodeAndAvailableIsTrue(String code);
}
