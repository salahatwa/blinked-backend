package com.blinked.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.api.common.repo.BaseRepository;
import com.blinked.entities.RefreshToken;
import com.blinked.entities.User;

import jakarta.transaction.Transactional;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, String> {
	@Transactional
	@Modifying
	@Query("UPDATE RefreshToken SET available = false WHERE user = ?1 AND available = true")
	public void disableOldRefreshTokens(User user);

	@Query("SELECT refresh FROM RefreshToken refresh JOIN FETCH refresh.user user JOIN FETCH user.roles WHERE refresh.code = ?1 AND refresh.available = true")
	public Optional<RefreshToken> findOptionalByCodeAndAvailableIsTrue(String code);
}
