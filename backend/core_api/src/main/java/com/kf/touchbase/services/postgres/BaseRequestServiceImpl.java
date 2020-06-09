package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.BaseJoin;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.services.AbstractEntityDataService;
import com.kf.touchbase.services.neo4j.PersonService;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;

@Singleton
public class BaseRequestServiceImpl extends AbstractEntityDataService<BaseJoin> implements BaseJoinService {
    private PersonService personService;

    public BaseRequestServiceImpl(SessionFactory sessionFactory,
                                  PersonService personService) {
        super(sessionFactory);
        this.personService = personService;
    }

    @Override
    public Class<BaseJoin> getEntityType() {
        return BaseJoin.class;
    }

    @Override
    public Iterable<BaseJoin> findAllByOwner(String username) {
        var session = sessionFactory.openSession();
        return session.loadAll(
                getEntityType(), new Filter("owner", ComparisonOperator.CONTAINING, username), 1);
    }

    @Override
    public BaseJoin createBaseJoin(String requesterUsername, BaseJoin baseJoin) {
        return null;
    }

    @Override
    public Success acceptBaseJoin(String requesterUsername, BaseJoin baseJoin) {
        return null;
    }

    @Override
    public Success deleteBaseJoin(String requesterUsername, String baseJoinId) throws SecurityException {
        return null;
    }
}
