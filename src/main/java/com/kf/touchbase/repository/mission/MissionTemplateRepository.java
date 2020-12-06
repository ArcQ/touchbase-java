package com.kf.touchbase.repository.mission;

import com.kf.touchbase.models.domain.MissionType;
import com.kf.touchbase.models.domain.mission.MissionTemplate;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

import java.util.UUID;

public interface MissionTemplateRepository extends RxJavaCrudRepository<MissionTemplate, UUID> {

    Flowable<MissionTemplate> findAll();

    Flowable<MissionTemplate> findAllByType(MissionType missionType);
}
