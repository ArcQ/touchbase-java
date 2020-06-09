package com.kf.touchbase.services.neo4j;

import com.kf.touchbase.models.domain.Person;
import com.kf.touchbase.services.DataService;

public interface PersonService extends DataService<Person> {
    Iterable<Person> findAll();

    Person findByUsername(String username);

    Class<Person> getEntityType();

    Person create(Person person);
}
