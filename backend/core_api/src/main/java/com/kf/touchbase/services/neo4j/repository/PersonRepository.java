package com.kf.touchbase.services.neo4j.repository;

import com.kf.touchbase.models.domain.neo4j.Person;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;

@Singleton
public class PersonRepository extends Neo4jRepository<Person> {

    public PersonRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Person> getEntityType() {
        return Person.class;
    }

    public Person findByUsername(String username) {
        var session = sessionFactory.openSession();
        return session.loadAll(
                getEntityType(), new Filter("username", ComparisonOperator.EQUALS, username), 1).iterator().next();
    }

    public Person findByAuthId(String authId) {
        var session = sessionFactory.openSession();
        return session.loadAll(
                getEntityType(), new Filter("authId", ComparisonOperator.EQUALS, authId), 1).iterator().next();
    }

    public Person create(Person person) {
        var session = sessionFactory.openSession();
        Filters filters = new Filters()
                .or(new Filter("username", ComparisonOperator.EQUALS, person.getUsername()))
                .or(new Filter("email", ComparisonOperator.EQUALS, person.getEmail()))
                .or(new Filter("authId", ComparisonOperator.EQUALS, person.getAuthId()));

        if (session.loadAll(getEntityType(), filters, 0).size() == 0) {
            return save(person);
        }

        throw new EntityExistsException("Person already exists");
    }
}
