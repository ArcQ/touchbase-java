package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.Success;

import javax.inject.Singleton;

@Singleton
public class BaseRequestServiceImpl implements BaseJoinService {
    private UserService userService;

    public BaseRequestServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Iterable<BaseJoin> findByAdmin(String adminAuthId) throws SecurityException {
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
