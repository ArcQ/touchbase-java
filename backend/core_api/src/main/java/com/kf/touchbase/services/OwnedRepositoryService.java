package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.TouchBaseEntityInterface;

import java.util.UUID;

public interface OwnedRepositoryService<T extends TouchBaseEntityInterface> extends RepositoryService<T> {

    default T findIfOwner(String ownerAuthId, TouchBaseEntityInterface entity) throws SecurityException {
        return findIfOwnerId(ownerAuthId, entity.getUuid(), (Class<T>) entity.getClass());
    }

    default T findIfOwnerId(String ownerAuthId, UUID entityId, Class<T> clazz) throws SecurityException {
        T foundEntity =
                findById(entityId).orElseThrow(() -> new IllegalArgumentException(String.format(
                        "User %s tried to access entity of " +
                        "type %s with id of %s but it doesn't exist", ownerAuthId,
                clazz.getName(), entityId)));

        if (!foundEntity.getOwnerId().equals(ownerAuthId)) {
            throw new SecurityException(String.format("User %s is not an owner but tried to to " +
                            "access entity of type %s with id of %s", ownerAuthId,
                    clazz.getName(), entityId));
        }

        return foundEntity;
    }

    Iterable<T> findByOwnerId(String ownerAuthId, Class<T> clazz);

}
