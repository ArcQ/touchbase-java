package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseJoinRepository extends JpaRepository<BaseJoin, UUID> {
    Iterable<BaseJoin> findByAdminId(String adminAuthId);
}
