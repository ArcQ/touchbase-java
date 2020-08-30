package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.services.postgres.repository.BaseJoinRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@RequiredArgsConstructor
@Singleton
public class BaseInviteServiceImpl implements BaseJoinService {

    private final BaseJoinRepository baseJoinRepository;

    @Override
    public Iterable<BaseJoin> findByAdmin(String adminAuthId) {
        return null;
    }

    @Override
    public BaseJoin createBaseJoin(String adminAuthId, BaseJoin baseJoin) throws SecurityException {
        return null;
    }

    @Override
    public Success acceptBaseJoin(String adminAuthId, BaseJoin baseJoin) throws SecurityException {
        return null;
    }

    @Override
    public Success deleteBaseJoin(String requesterAuthId, String baseJoinId) throws SecurityException {
        return null;
    }
}
