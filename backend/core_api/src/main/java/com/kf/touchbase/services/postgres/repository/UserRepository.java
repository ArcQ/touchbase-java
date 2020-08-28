package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.User;
import io.micronaut.context.annotation.Primary;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Iterable<User> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String queryUsername, String queryFirstName, String queryFullName);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByAuthId(String authId);
}
