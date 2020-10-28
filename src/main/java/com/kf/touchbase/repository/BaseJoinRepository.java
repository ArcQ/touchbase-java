package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

import java.util.UUID;

@Repository
public interface BaseJoinRepository extends RxJavaCrudRepository<BaseJoin, UUID> {
    @Query(value = "SELECT b.* FROM base_join bj, tb_user u WHERE m.base_id=b.id AND m" +
            ".member_id=u.id AND u.auth_key=:userAuthKey",
            nativeQuery = true)
    Flowable<BaseJoin> findAllByUserAuthKeyAndBaseJoinAction(
            String userAuthKey,
            BaseJoinAction baseJoinAction);
}
