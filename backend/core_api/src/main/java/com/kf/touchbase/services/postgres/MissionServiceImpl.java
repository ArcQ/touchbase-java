package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.Mission;
import com.kf.touchbase.services.AbstractDataService;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;

@Singleton
public class MissionServiceImpl extends AbstractDataService<Mission> implements MissionService {

    public MissionServiceImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Iterable<Mission> findOwnMissions(String username) {
        return null;
    }

    @Override
    public Class<Mission> getEntityType() {
        return Mission.class;
    }
}
