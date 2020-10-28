package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.Base;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import java.util.UUID;

@Repository
public interface BaseRepository extends RxJavaCrudRepository<Base, UUID> {

    @Query(value = "SELECT b.* FROM base b, base_member m, tb_user u WHERE m.base_id=b.id AND m" +
            ".member_auth_key=:userAuthKey AND u.auth_key=:userAuthKey",
            nativeQuery = true)
    Flowable<Base> findAllByMemberUserAuthKey(String userAuthKey);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.id=:baseId AND m" +
            ".base_id=:baseId AND m.member_auth_key=:userAuthKey AND u.auth_key=:userAuthKey",
            nativeQuery = true)
    Maybe<Base> findIfMember(UUID baseId, String userAuthKey);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.id = :baseId AND m" +
            ".base_id=b.id AND m.member_auth_key=:userAuthKey AND u.auth_key=:userAuthKey AND m" +
            ".role='ADMIN'",
            nativeQuery = true)
    Maybe<Base> findIfMemberAdmin(UUID baseId, String userAuthKey);
}
