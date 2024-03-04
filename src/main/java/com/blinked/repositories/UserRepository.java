package com.blinked.repositories;

import java.util.Optional;

import com.api.common.repo.BaseRepository;
import com.blinked.entities.User;

public interface UserRepository extends BaseRepository<User, Long> {
	Optional<User> findById(Long id);

//	Optional<User> findByIdFetchRoles(Long id);

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	void deleteById(Long id);

	void deleteAll(Iterable<? extends User> entities);

	void delete(User user);

	User save(User user);

//	Collection<User> saveAll(Collection<User> users);
}
