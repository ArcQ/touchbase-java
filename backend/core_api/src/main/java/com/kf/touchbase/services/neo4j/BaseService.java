package com.kf.touchbase.services.neo4j;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.services.DataService;

public interface BaseService extends DataService<Base> {
    Iterable<Base> findAll();

    Success makeBaseInactive(String ownerUsername, String baseId) throws SecurityException;

    Class<Base> getEntityType();

    Base createBase(String creatorUsername, Base newBase);

    Base patchBase(String ownerUserName, Base updateBase) throws SecurityException;

    Base addUserToBaseAsOwner(String ownerUsername, String addUsername, String baseId) throws SecurityException;

    Base deleteUserFromBaseAsOwner(String ownerUsername, String addUsername, String baseId) throws SecurityException;
}
