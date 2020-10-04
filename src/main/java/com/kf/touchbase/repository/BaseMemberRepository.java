package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.BaseMember;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;

import java.util.UUID;

@Repository
public interface BaseMemberRepository extends RxJavaCrudRepository<BaseMember, UUID> {
}
