package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.Base;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseRepository extends RxJavaCrudRepository<Base, UUID> {

    @Query(value = "SELECT b.* FROM base b, base_member m, tb_user u WHERE m.base_id=b.id AND m" +
            ".member_id=u.id AND u.auth_key=:userAuthKey",
            nativeQuery = true)
    Flowable<Base> findAllByMembersUserAuthKey(String userAuthKey);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.id='?1' AND m.base_id=b" +
            ".id AND m.member_id=u.id AND u.auth_key='?2'",
            nativeQuery = true)
    Maybe<Base> findIfMember(UUID baseId, String userAuthKey);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.id = ?1 AND m.base_id=b" +
            ".id AND m.member_id=u.id AND u.auth_key='?2' AND m.role='ADMIN'",
            nativeQuery = true)
    Optional<Base> findIfMemberAdmin(UUID baseId, String userAuthKey);
}
