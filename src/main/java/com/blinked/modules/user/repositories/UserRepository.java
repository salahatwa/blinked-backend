package com.blinked.modules.user.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blinked.modules.core.utils.Page;
import com.blinked.modules.user.entities.User;

@Repository
public interface UserRepository {
    Page<User> findAll(Pageable pageable);
    Optional<User> findById(Long id);
    Optional<User> findByIdFetchRoles(Long id);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    void deleteById(Long id);
    void deleteAll(Iterable<? extends User> entities);
    void delete(User user);
    User save(User user);
    Collection<User> saveAll(Collection<User> users);
}
