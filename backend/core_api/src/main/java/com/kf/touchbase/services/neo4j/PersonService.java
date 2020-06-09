package com.kf.touchbase.services.neo4j;

import com.kf.touchbase.models.domain.Person;

public interface PersonService extends DataService<Person> {
    Iterable<Person> findAll();

    Person findByUsername(String username);

    Class<Person> getEntityType();

    Person create(Person person);
}
