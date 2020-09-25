package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String queryUsername, String queryFirstName, String queryFullName);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByAuthKey(String AuthKey);

    Optional<User> findAllById(List<UUID> uuids);
}
