package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Maybe;

import java.util.UUID;

@Repository
public interface BaseMemberRepository extends RxJavaCrudRepository<BaseMember, UUID> {
    Maybe<BaseMember> findByUserAuthKeyAndBaseId(String userAuthKey, UUID baseId);

    Maybe<BaseMember> findByUserAuthKeyAndBaseIdAndRole(String userAuthKey, UUID baseId, Role role);
}
