package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.services.OwnedRepositoryService;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface BaseJoinRepository extends CrudRepository<BaseJoin, UUID>,
        OwnedRepositoryService<BaseJoin> {
}
