package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.BaseJoin;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.services.DataService;

public interface BaseJoinService extends DataService<BaseJoin> {
    Class<BaseJoin> getEntityType();

    Iterable<BaseJoin> findAllByOwner(String username);

    BaseJoin createBaseJoin(String requesterUsername, BaseJoin baseJoin);

    Success acceptBaseJoin(String requesterUsername, BaseJoin baseJoin);

    Success deleteBaseJoin(String requesterUsername, String baseJoinId) throws SecurityException;
}
