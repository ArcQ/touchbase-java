package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.domain.BaseJoin;
import com.kf.touchbase.models.domain.Success;

public interface BaseJoinService extends DataService<Base> {
    Iterable<Base> findAll();

    Class<Base> getEntityType();

    BaseJoin createBaseJoinAsOwner(String requesterUsername, BaseJoin baseJoin);

    BaseJoin createBaseRequest(String creatorUsername, BaseJoin baseJoin);

    Success acceptBaseRequest(String requestedUsername, String baseJoinId);

    Success acceptBaseInvite(String invitedUsername, String baseJoinId);

    Success deleteBaseJoin(String requesterUsername, String baseJoinId) throws SecurityException;
}
