package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.Mission;
import com.kf.touchbase.services.neo4j.DataService;

public interface MissionService extends DataService<Mission> {
    Iterable<Mission> findOwnMissions(String username);

    Class<Mission> getEntityType();
}
