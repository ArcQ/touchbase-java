package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.Person;
import com.kf.touchbase.services.postgres.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;

@RequiredArgsConstructor
@Singleton
public class PersonService {

    private final PersonRepository personRepository;

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Iterable<Person> searchByQuery(String query) {
        return personRepository.findByUsernameContainsOrFirstNameContainsOrLastNameContains(query, query, query);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Person findByAuthId(String authId) {
        return personRepository.findByAuthId(authId);
    }

    public Person create(Person person) {
        if (personRepository.findByEmail(person.getEmail()) == null) {
            throw new EntityExistsException("A user is already registered under this email.");
        }
        return personRepository.save(person);
    }
}
