package com.kf.touchbase.repository.mission;

import com.kf.touchbase.models.domain.mission.Mission;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

import java.util.UUID;

public interface MissionRepository extends RxJavaCrudRepository<Mission, UUID> {

    Flowable<Mission> findAllByUserAuthKey(String authKey);

    Flowable<Mission> findAllByUserAuthKeyByBaseId(String authKey, UUID baseId);
}
