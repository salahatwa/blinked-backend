package com.blinked.modules.user.repositories;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blinked.modules.core.utils.Page;
import com.blinked.modules.user.entities.User;

@Repository
public interface NativeQueryUserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
