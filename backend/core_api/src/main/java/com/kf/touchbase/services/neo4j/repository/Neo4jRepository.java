package com.kf.touchbase.services.neo4j.repository;

import com.kf.touchbase.models.domain.neo4j.TouchBaseNeo4jDomain;
import com.kf.touchbase.services.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.session.SessionFactory;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class Neo4jRepository<T extends TouchBaseNeo4jDomain> implements RepositoryService<T> {
    protected final SessionFactory sessionFactory;

    protected static final int DEPTH_LIST = 0;
    protected static final int DEPTH_ENTITY = 1;

    @Override
    public Iterable<T> findAll() {
        var session = sessionFactory.openSession();
        return session.loadAll(getEntityType(), DEPTH_LIST);
    }

    @Override
    public Optional<T> findById(UUID id) {
        var session = sessionFactory.openSession();
        return Optional.of(session.load(getEntityType(), id, 1));
    }

    @Override
    public void deleteById(UUID id) {
        var session = sessionFactory.openSession();
        session.delete(session.load(getEntityType(), id));
    }

    @Override
    public T save(T entity) {
        var session = sessionFactory.openSession();
        session.save(entity, DEPTH_ENTITY);
        return findById(entity.getUuid()).orElseThrow(EntityNotFoundException::new);
    }

    protected abstract Class<T> getEntityType();
}
