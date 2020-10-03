package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.Mission;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

import java.util.UUID;

public interface MissionRepository extends RxJavaCrudRepository<Mission, UUID> {

    Flowable<Mission> findAllByUserAuthKey(String authKey);
}
