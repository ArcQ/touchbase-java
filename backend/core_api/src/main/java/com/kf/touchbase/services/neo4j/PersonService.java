package com.kf.touchbase.services.neo4j;

import com.kf.touchbase.models.domain.neo4j.Person;
import com.kf.touchbase.services.neo4j.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@RequiredArgsConstructor
@Singleton
public class PersonService {

    private final PersonRepository personRepository;

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Iterable<Person> searchByQuery(String query) {
        return personRepository.searchByQuery(query);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Person findByAuthId(String authId) {
        return personRepository.findByAuthId(authId);
    }

    public Person create(Person person) {
        return personRepository.create(person);
    }
}
