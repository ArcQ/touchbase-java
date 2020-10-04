package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Maybe;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Repository
public interface UserRepository extends RxJavaCrudRepository<User, UUID> {

    List<User> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String queryUsername, String queryFirstName, String queryFullName);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Maybe<User> findByAuthKey(String authKey);

    Optional<User> findAllById(List<UUID> uuids);
}
