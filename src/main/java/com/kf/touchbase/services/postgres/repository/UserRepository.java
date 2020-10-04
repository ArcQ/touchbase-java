package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.User;
import io.micronaut.data.repository.PageableRepository;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public interface UserRepository extends PageableRepository<User, UUID> {

    Iterable<User> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String queryUsername,
                                                                               String queryFirstName,
                                                                               String queryFullName);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByAuthId(String authId);
}
