package com.blinked.modules.user.repositories;

import static com.blinked.modules.user.repositories.QueriesUser.COUNT_ENABLED_USERS;
import static com.blinked.modules.user.repositories.QueriesUser.FIND_ALL_USER_FETCH_ROLES;
import static com.blinked.modules.user.repositories.QueriesUser.FIND_BY_FIELD_FETCH_ROLES;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.blinked.modules.core.utils.Page;
import com.blinked.modules.user.entities.User;

@Repository
public class NativeQueryUserRepositoryImpl implements NativeQueryUserRepository {

    @Autowired
    EntityManager manager;

    @Override
    public Optional<User> findById(Long id) {
        Query query = manager
                .createNativeQuery(format(FIND_BY_FIELD_FETCH_ROLES, "u.id = :user_id"), Tuple.class)
                    .setParameter("user_id", id);
        try {
            Tuple tuple = (Tuple) query.getSingleResult();
            return of(User.from(tuple));
        } catch (NoResultException exception) {
            return empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
    	Query query = manager
                .createNativeQuery(format(FIND_BY_FIELD_FETCH_ROLES, "u.email = :user_email"), Tuple.class)
                    .setParameter("user_email", email);
        try {
        	Tuple tuple = (Tuple) query.getSingleResult();
            return of(User.from(tuple));
        } catch (NoResultException exception) {
            return empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<User> findAll(Pageable pageable) {
    	Query query = manager
                .createNativeQuery(FIND_ALL_USER_FETCH_ROLES, Tuple.class);

        long count = ((BigInteger) manager
                .createNativeQuery(COUNT_ENABLED_USERS)
                .getSingleResult())
                    .longValue();

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<Tuple> content = query.getResultList();

        List users = content.stream().map(User::from).collect(Collectors.toList());

        return Page.of(users, pageNumber, pageSize, count);
    }
}
