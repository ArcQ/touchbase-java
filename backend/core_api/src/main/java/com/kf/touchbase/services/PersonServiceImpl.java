package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Person;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;

@Singleton
public class PersonServiceImpl extends AbstractDataService<Person> implements PersonService {

    public PersonServiceImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Iterable<Person> findAll() {
        var session = sessionFactory.openSession();
        return session.loadAll(Person.class, 1);
    }

    @Override
    public Class<Person> getEntityType() {
        return Person.class;
    }

    @Override
    public Person getByAuthId(String authId) {
        var session = sessionFactory.openSession();
        return session.loadAll(
                getEntityType(), new Filter("authId", ComparisonOperator.CONTAINING, authId), 1).iterator().next();
    }

    public Person getByUsername(String username) {
        var session = sessionFactory.openSession();
		return session.loadAll(
			getEntityType(), new Filter("username", ComparisonOperator.CONTAINING, username), 1).iterator().next();
    }


}
