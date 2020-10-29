package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

import java.util.UUID;

@Repository
public interface BaseJoinRepository extends RxJavaCrudRepository<BaseJoin, UUID> {
    @Query(value = "SELECT j.* FROM base_join j, tb_user u, base b WHERE b.id=j.base_id AND " +
            "j.joining_user_auth_key=:userAuthKey AND u.auth_key=:userAuthKey AND j" +
            ".base_join_action=:baseJoinAction",
            nativeQuery = true)
    Flowable<BaseJoin> findAllByUserAuthKeyAndBaseJoinAction(
            String userAuthKey,
            String baseJoinAction);
}
