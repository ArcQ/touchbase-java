package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.repository.BaseJoinRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;

@RequiredArgsConstructor
@Singleton
public class BaseInviteServiceImpl implements BaseJoinService {

    private final BaseJoinRepository baseJoinRepository;

    @Override
    public List<BaseJoin> findByAdmin(String adminAuthKey) {
        return null;
    }

    @Override
    public BaseJoin createBaseJoin(String adminAuthKey, BaseJoin baseJoin) throws SecurityException {
        return null;
    }

    @Override
    public Success acceptBaseJoin(String adminAuthKey, BaseJoin baseJoin) throws SecurityException {
        return null;
    }

    @Override
    public Success deleteBaseJoin(String requesterAuthKey, String baseJoinId) throws SecurityException {
        return null;
    }
}
