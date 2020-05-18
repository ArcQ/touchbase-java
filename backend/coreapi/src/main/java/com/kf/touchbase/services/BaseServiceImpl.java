package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;
import org.neo4j.ogm.session.SessionFactory;

public class BaseServiceImpl extends AbstractDataService<Base> implements BaseService {
    public BaseServiceImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Iterable<Base> findAll() {
        var session = sessionFactory.openSession();
        return session.loadAll(Base.class, 1);
    }

    @Override
    public Class<Base> getEntityType() {
        return Base.class;
    }
}
