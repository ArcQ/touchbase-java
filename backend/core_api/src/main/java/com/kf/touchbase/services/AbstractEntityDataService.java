package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.TouchBaseEntity;
import com.kf.touchbase.services.neo4j.DataService;
import org.neo4j.ogm.session.SessionFactory;

import java.util.UUID;

public abstract class AbstractEntityDataService<T extends TouchBaseEntity> extends AbstractDataService<T> implements DataService<T> {

    public AbstractEntityDataService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public T findIfOwner(String username, T entity) throws SecurityException {
        return findIfOwnerId(username, entity.getUuid(), (Class<T>) entity.getClass());
    }

    public T findIfOwnerId(String username, UUID entityId, Class<T> clazz) throws SecurityException {
        T foundEntity = find(entityId);
        if (foundEntity == null) {
            throw new IllegalArgumentException(String.format("User %s tried to access entity of " +
                            "type %s with id of %s but it doesn't exist", username,
                    clazz.getName(), entityId));
        }

        if (!foundEntity.getOwner().getUsername().equals(username)) {
            throw new SecurityException(String.format("User %s is not an owner but tried to to " +
                            "access entity of type %s with id of %s", username,
                    clazz.getName(), entityId));
        }

        return foundEntity;
    }

}
