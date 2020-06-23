package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.Success;

public interface BaseJoinService {
    Iterable<BaseJoin> findAllByOwner(String cognitoId);

    BaseJoin createBaseJoin(String ownerAuthId, BaseJoin baseJoin) throws SecurityException;

    Success acceptBaseJoin(String ownerAuthId, BaseJoin baseJoin) throws SecurityException;

    Success deleteBaseJoin(String ownerAuthId, String baseJoinId) throws SecurityException;
}
