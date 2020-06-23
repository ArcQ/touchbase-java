package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.Success;
import com.kf.touchbase.services.neo4j.PersonService;

import javax.inject.Singleton;

@Singleton
public class BaseRequestServiceImpl implements BaseJoinService {
    private PersonService personService;

    public BaseRequestServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Iterable<BaseJoin> findAllByOwner(String ownerAuthId) throws SecurityException {
        return null;
    }

    @Override
    public BaseJoin createBaseJoin(String requesterAuthId, BaseJoin baseJoin) throws SecurityException {
        return null;
    }

    @Override
    public Success acceptBaseJoin(String requesterAuthId, BaseJoin baseJoin) throws SecurityException {
        return null;
    }

    @Override
    public Success deleteBaseJoin(String requesterAuthId, String baseJoinId) throws SecurityException {
        return null;
    }
}
