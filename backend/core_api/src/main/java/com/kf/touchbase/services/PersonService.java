package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Person;

public interface PersonService extends DataService<Person> {
    Iterable<Person> findAll();

    Person getByUsername(String username);

    Class<Person> getEntityType();

    Person create(Person person);
}
