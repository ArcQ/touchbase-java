package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.TouchBasePostgresEntity;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

public interface OwnedRepositoryService<T extends TouchBasePostgresEntity> extends CrudRepository<T, UUID> {

    default T findIfOwner(String ownerAuthId, TouchBasePostgresEntity entity) throws SecurityException {
        return findIfOwnerId(ownerAuthId, entity.getUuid(), entity.getClass());
    }

    default T findIfOwnerId(String ownerAuthId, UUID entityId, Class<?> clazz) throws SecurityException {
        T foundEntity =
                findById(entityId).orElseThrow(() -> new IllegalArgumentException(String.format(
                        "User %s tried to access entity of " +
                        "type %s with id of %s but it doesn't exist", ownerAuthId,
                clazz.getName(), entityId)));

        if (foundEntity.getOwners().stream().noneMatch((owner) -> owner.getAuthId().equals(ownerAuthId))) {
            throw new SecurityException(String.format("User %s is not an owner but tried to to " +
                            "access entity of type %s with id of %s", ownerAuthId,
                    clazz.getName(), entityId));
        }

        return foundEntity;
    }
}
