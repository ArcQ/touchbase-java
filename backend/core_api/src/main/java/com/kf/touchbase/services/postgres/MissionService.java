package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.Mission;

import javax.inject.Singleton;

@Singleton
public class MissionService {

    public Iterable<Mission> findOwnMissions(String requesterAuthId) {
        return null;
    }
}
