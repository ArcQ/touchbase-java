package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.TouchBasePostgresEntity;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

public interface OwnedRepositoryService<T extends TouchBasePostgresEntity> extends CrudRepository<T, UUID> {

    default T findIfAdmin(String adminAuthId, TouchBasePostgresEntity entity) throws SecurityException {
        return findifAdminId(adminAuthId, entity.getUuid(), entity.getClass());
    }

    default T findifAdminId(String adminAuthId, UUID entityId, Class<?> clazz) throws SecurityException {
        T foundEntity =
                findById(entityId).orElseThrow(() -> new IllegalArgumentException(String.format(
                        "User %s tried to access entity of " +
                        "type %s with id of %s but it doesn't exist", adminAuthId,
                clazz.getName(), entityId)));

        if (foundEntity.getAdmins().stream().noneMatch((admin) -> admin.getAuthId().equals(adminAuthId))) {
            throw new SecurityException(String.format("User %s is not an admin but tried to to " +
                            "access entity of type %s with id of %s", adminAuthId,
                    clazz.getName(), entityId));
        }

        return foundEntity;
    }
}
