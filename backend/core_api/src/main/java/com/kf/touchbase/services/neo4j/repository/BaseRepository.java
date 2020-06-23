package com.kf.touchbase.services.neo4j.repository;

import com.kf.touchbase.models.domain.neo4j.Base;
import com.kf.touchbase.services.OwnedRepositoryService;
import org.apache.commons.lang3.NotImplementedException;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;

@Singleton
public class BaseRepository extends Neo4jRepository<Base> implements OwnedRepositoryService<Base> {

    public BaseRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Base> getEntityType() {
        return Base.class;
    }

    @Override
    public Iterable<Base> findByOwnerId(String ownerAuthId, Class<Base> clazz) {
        throw new NotImplementedException("Method not implemented for base repository");
    }
}
