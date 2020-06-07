package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.TouchBaseEntity;
import org.neo4j.ogm.session.SessionFactory;

abstract class AbstractEntityDataService<T extends TouchBaseEntity> extends AbstractDataService<T> implements DataService<T> {

    public AbstractEntityDataService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public T findIfOwner(String username, T entity) throws SecurityException {
        T foundEntity = find(entity.getUuid());
        if (foundEntity == null) {
            throw new IllegalArgumentException(String.format("User %s tried to access entity of " +
                            "type %s with id of %s but it doesn't exist", username,
                    entity.getClass().getName(), entity.getUuid()));
        }

        if (!foundEntity.getOwner().getUsername().equals(username)) {
            throw new SecurityException(String.format("User %s is not an owner but tried to to " +
                    "access entity of type %s with id of %s", username,
                    entity.getClass().getName(), entity.getUuid()));
        }

        return entity;
    }
}
