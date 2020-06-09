package com.kf.touchbase.graphql;

import com.kf.touchbase.models.domain.Person;
import com.kf.touchbase.services.neo4j.PersonService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class PersonFetcher implements DataFetcher<Person> {

    private final PersonService personService;

    @Override
    public Person get(DataFetchingEnvironment env) {
        String username = env.getArgument("username");
        return personService.findByUsername(username);
    }
}
