package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.TouchBasePostgresEntity;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

public interface OwnedRepositoryService<T extends TouchBasePostgresEntity> extends CrudRepository<T, UUID> {

    default T findIfAdmin(String adminAuthKey, TouchBasePostgresEntity entity) throws SecurityException {
        return findIfAdminId(adminAuthKey, entity.getUuid(), entity.getClass());
    }

    default T findIfAdminId(String adminAuthKey, UUID entityId, Class<?> clazz) throws SecurityException {
        T foundEntity =
                findById(entityId).orElseThrow(() -> new IllegalArgumentException(String.format(
                        "User %s tried to access entity of " +
                        "type %s with id of %s but it doesn't exist", adminAuthKey,
                clazz.getName(), entityId)));

        if (foundEntity.getAdmins().stream().noneMatch((admin) -> admin.getAuthKey().equals(adminAuthKey))) {
            throw new SecurityException(String.format("User %s is not an admin but tried to to " +
                            "access entity of type %s with id of %s", adminAuthKey,
                    clazz.getName(), entityId));
        }

        return foundEntity;
    }
}
