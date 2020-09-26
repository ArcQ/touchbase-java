package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface BaseJoinRepository extends JpaRepository<BaseJoin, UUID> {
}
