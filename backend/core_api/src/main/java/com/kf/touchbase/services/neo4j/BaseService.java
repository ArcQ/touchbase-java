package com.kf.touchbase.services.neo4j;

import com.kf.touchbase.models.domain.neo4j.Base;
import com.kf.touchbase.models.domain.neo4j.Person;
import com.kf.touchbase.models.domain.neo4j.TouchBaseNeo4jDomain;
import com.kf.touchbase.models.domain.postgres.Success;
import com.kf.touchbase.services.neo4j.repository.BaseRepository;
import com.kf.touchbase.utils.TouchbaseBeanUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class BaseService {
    private final PersonService personService;
    private final BaseRepository baseRepository;

    public Base createBase(String ownerAuthId, Base newBase) {
        var creator = personService.findByAuthId(ownerAuthId);
        newBase.setCreator(creator);
        newBase.setOwner(creator);
        newBase.setMembers(Collections.singleton(creator));
        return baseRepository.save(newBase);
    }

    public Base patchBase(String ownerAuthId, UUID baseId, Base updateBase) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, baseId, Base.class);
        TouchbaseBeanUtils.mergeInNotNull(
                existingBase, updateBase, TouchBaseNeo4jDomain.class, "email", "name",
                "imageUrl");
        return baseRepository.save(existingBase);
    }

    public Base addUserToBaseAsOwner(String ownerAuthId, String addAuthId, String baseId) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, UUID.fromString(baseId), Base.class);
        Person person = personService.findByAuthId(addAuthId);
        existingBase.getMembers().add(person);
        baseRepository.save(existingBase);
        return null;
    }

    public Base deleteUserFromBaseAsOwner(String ownerAuthId, String addAuthId,
                                          String baseId) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, UUID.fromString(baseId), Base.class);
        Person person = personService.findByAuthId(addAuthId);
        existingBase.getMembers().remove(person);
        baseRepository.save(existingBase);
        return null;
    }

    public Success makeBaseInactive(String ownerAuthId, UUID baseId) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, baseId, Base.class);
        existingBase.setActive(false);
        baseRepository.save(existingBase);
        return null;
    }
}
