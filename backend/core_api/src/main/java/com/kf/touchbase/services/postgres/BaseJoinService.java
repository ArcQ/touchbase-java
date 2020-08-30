package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.Success;

public interface BaseJoinService {
    Iterable<BaseJoin> findByAdmin(String cognitoId);

    BaseJoin createBaseJoin(String adminAuthId, BaseJoin baseJoin) throws SecurityException;

    Success acceptBaseJoin(String adminAuthId, BaseJoin baseJoin) throws SecurityException;

    Success deleteBaseJoin(String adminAuthId, String baseJoinId) throws SecurityException;
}
