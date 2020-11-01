package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

import java.util.UUID;

@Repository
public interface BaseJoinRepository extends RxJavaCrudRepository<BaseJoin, UUID> {

    @Query(value = "SELECT j.* FROM base_join j, base b, base_member m WHERE b.id=j.base_id AND " +
            "b.id=m.base_id AND m.member_auth_key=:userAuthKey AND m.role='ADMIN' AND j" +
            ".base_join_action='REQUEST'", nativeQuery = true)
    Flowable<BaseJoin> findRequestsToMyBases(String userAuthKey);

    @Query(value = "SELECT j.* FROM base_join j WHERE j.joining_user_auth_key=:" +
            "userAuthKey AND j.base_join_action='INVITE'", nativeQuery = true)
    Flowable<BaseJoin> findInvitesToMe(String userAuthKey);

    @Query(value = "SELECT j.* FROM base_join j WHERE j.creator_auth_key=:userAuthKey AND j" +
            ".base_join_action='INVITE'", nativeQuery = true)
    Flowable<BaseJoin> findMyCreatedInvites(String userAuthKey);

    @Query(value = "SELECT j.* FROM base_join j WHERE j.creator_auth_key=:userAuthKey AND j" +
            ".base_join_action='REQUEST'", nativeQuery = true)
    Flowable<BaseJoin> findMyCreatedRequests(String userAuthKey);
}
