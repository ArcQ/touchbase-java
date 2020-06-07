package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.domain.BaseJoin;
import com.kf.touchbase.models.domain.Success;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;

@Singleton
public class BaseJoinServiceImpl extends AbstractEntityDataService<Base> implements BaseJoinService {
    private PersonService personService;

    public BaseJoinServiceImpl(SessionFactory sessionFactory,
                               PersonService personService) {
        super(sessionFactory);
        this.personService = personService;
    }

    @Override
    public Class<Base> getEntityType() {
        return null;
    }

    @Override
    public BaseJoin createBaseJoinAsOwner(String requesterUsername, BaseJoin baseJoin) {
        return null;
    }

    @Override
    public BaseJoin createBaseRequest(String creatorUsername, BaseJoin baseJoin) {
        return null;
    }

    @Override
    public Success acceptBaseRequest(String requestedUsername, String baseJoinId) {
        return null;
    }

    @Override
    public Success acceptBaseInvite(String invitedUsername, String baseJoinId) {
        return null;
    }

    @Override
    public Success deleteBaseJoin(String requesterUsername, String baseJoinId) throws SecurityException {
        return null;
    }
}
