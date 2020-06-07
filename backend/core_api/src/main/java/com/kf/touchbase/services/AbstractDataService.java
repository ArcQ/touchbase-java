package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.TouchBaseDomain;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.session.SessionFactory;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractDataService<T extends TouchBaseDomain> implements DataService<T> {
    protected final SessionFactory sessionFactory;

    protected static final int DEPTH_LIST = 0;
    protected static final int DEPTH_ENTITY = 1;

    @Override
    public Iterable<T> findAll() {
        var session = sessionFactory.openSession();
        return session.loadAll(getEntityType(), DEPTH_LIST);
    }

    @Override
    public T find(UUID id) {
        var session = sessionFactory.openSession();
        return session.load(getEntityType(), id, DEPTH_ENTITY);
    }

    @Override
    public void delete(UUID id) {
        var session = sessionFactory.openSession();
        session.delete(session.load(getEntityType(), id));
    }

    @Override
    public T createOrUpdate(T entity) {
        var session = sessionFactory.openSession();
        session.save(entity, DEPTH_ENTITY);
        return find(entity.getUuid());
    }

    abstract Class<T> getEntityType();
}
