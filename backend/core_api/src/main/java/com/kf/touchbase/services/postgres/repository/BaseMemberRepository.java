package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.BaseMember;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface BaseMemberRepository extends CrudRepository<BaseMember, UUID> {
}
