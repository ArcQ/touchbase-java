package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.ExpoPushToken;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import java.util.UUID;

@Repository
public interface ExpoPushTokenRepository extends RxJavaCrudRepository<ExpoPushToken, UUID> {

    Flowable<ExpoPushToken> findAllByUserAuthKey(String userAuthKey);

    Maybe<ExpoPushToken> findByUserAuthKeyAndDeviceId(String userAuthKey, String deviceId);
}
