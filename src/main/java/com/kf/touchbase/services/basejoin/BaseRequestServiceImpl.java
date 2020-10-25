package com.kf.touchbase.services.basejoin;

import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BaseRequestServiceImpl implements BaseJoinService {

    public BaseRequestServiceImpl() {
    }

    @Override
    public List<BaseJoin> findByAdmin(String adminAuthKey) throws SecurityException {
        return null;
    }

    @Override
    public BaseJoin createBaseJoin(String requesterAuthKey, BaseJoin baseJoin)
            throws SecurityException {
        return null;
    }

    @Override
    public Success acceptBaseJoin(String requesterAuthKey, BaseJoin baseJoin)
            throws SecurityException {
        return null;
    }

    @Override
    public Success deleteBaseJoin(String requesterAuthKey, String baseJoinId)
            throws SecurityException {
        return null;
    }
}
