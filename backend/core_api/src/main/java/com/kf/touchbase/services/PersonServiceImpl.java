package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Person;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;
import javax.persistence.EntityExistsException;

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
    public Person getByUsername(String username) {
        var session = sessionFactory.openSession();
		return session.loadAll(
			getEntityType(), new Filter("username", ComparisonOperator.CONTAINING, username), 1).iterator().next();
    }

    @Override
    public Person create(Person person) {
        var session = sessionFactory.openSession();
        Filters filters = new Filters()
                .or(new Filter("username", ComparisonOperator.CONTAINING, person.getUsername()))
                .or(new Filter("email", ComparisonOperator.CONTAINING, person.getEmail()))
                .or(new Filter("authId", ComparisonOperator.CONTAINING, person.getAuthId()));

         if (session.loadAll(getEntityType(), filters, 0).size() == 0) {
             return createOrUpdate(person);
         }

         throw new EntityExistsException("Person already exists");
    }
}
