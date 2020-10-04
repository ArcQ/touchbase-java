package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;

import java.util.List;

public interface BaseJoinService {
    List<BaseJoin> findByAdmin(String cognitoId);

    BaseJoin createBaseJoin(String adminAuthKey, BaseJoin baseJoin) throws SecurityException;

    Success acceptBaseJoin(String adminAuthKey, BaseJoin baseJoin) throws SecurityException;

    Success deleteBaseJoin(String adminAuthKey, String baseJoinId) throws SecurityException;
}
