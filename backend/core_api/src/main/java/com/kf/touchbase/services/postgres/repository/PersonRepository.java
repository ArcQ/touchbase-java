package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.Person;
import io.micronaut.data.repository.CrudRepository;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public interface PersonRepository extends CrudRepository<Person, UUID> {

    Iterable<Person> findByUsernameContainsOrFirstNameContainsOrLastNameContains(String queryUsername, String queryFirstName, String queryFullName);

    Person findByUsername(String username);

    Person findByEmail(String email);

    Person findByAuthId(String authId);
}
