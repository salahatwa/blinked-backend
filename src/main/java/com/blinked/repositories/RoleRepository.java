package com.blinked.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blinked.entities.Role;
import com.blinked.repositories.base.BaseRepository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

	@Override
	@Modifying
	@Transactional
	void deleteById(Long id);

	@Override
	@Transactional
	default void delete(Role role) {
		deleteById(role.getId());
	}

	@Override
	@Transactional
	default void deleteAll(Iterable<? extends Role> entities) {
		entities.forEach(entity -> deleteById(entity.getId()));
	}

	Optional<Role> findOptionalByName(String name);

	Boolean existsByName(String name);
}