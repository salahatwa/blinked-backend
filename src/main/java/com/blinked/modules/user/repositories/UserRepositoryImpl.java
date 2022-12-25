package com.blinked.modules.user.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blinked.modules.core.utils.Page;
import com.blinked.modules.user.entities.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springData;
    private final NativeQueryUserRepository queryNative;

    @Autowired
    public UserRepositoryImpl(
        SpringDataUserRepository springData,
        NativeQueryUserRepository queryNative
    ) {
        this.springData = springData;
        this.queryNative = queryNative;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return queryNative.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return queryNative.findById(id);
    }

    @Override
    public Optional<User> findByIdFetchRoles(Long id) {
        return this.springData.findByIdFetchRoles(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return queryNative.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return springData.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        springData.deleteById(id);
    }

    @Override
    public void delete(User user) {
        springData.delete(user);
    }

    @Override
    public User save(User user) {
        return springData.save(user);
    }

    @Override
    public Collection<User> saveAll(Collection<User> users) {
        return springData.saveAll(users);
    }

    @Override
    public void deleteAll(Iterable<? extends User> users) {
        springData.deleteAll(users);
    }
}
