package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.Person;
import com.kf.touchbase.models.domain.postgres.Success;
import com.kf.touchbase.services.postgres.repository.BaseRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class BaseService {
    private final PersonService personService;
    private final BaseRepository baseRepository;

    public Base createBase(String ownerAuthId, Base newBase) {
        var creator = personService.findByAuthId(ownerAuthId);
        newBase.setCreator(creator);
        newBase.setOwners(Set.of(creator));
        newBase.setMembers(Collections.singleton(creator));
        return baseRepository.save(newBase);
    }

    public Base patchBase(String ownerAuthId, UUID baseId, Base updateBase) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, baseId, Base.class);
        existingBase.mergeInNotNull(updateBase);
        return baseRepository.save(existingBase);
    }

    public Base addUserToBaseAsOwner(String ownerAuthId, String addAuthId, String baseId) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, UUID.fromString(baseId),
                Base.class);
        Person person = personService.findByAuthId(addAuthId);
        existingBase.getMembers().add(person);
        baseRepository.save(existingBase);
        return null;
    }

    public Base deleteUserFromBaseAsOwner(String ownerAuthId, String removeAuthId,
                                          String baseId) throws SecurityException {
        Base existingBase = baseRepository.findIfOwnerId(ownerAuthId, UUID.fromString(baseId),
                Base.class);
        existingBase.getMembers().removeIf((person) -> person.getAuthId().equals(removeAuthId));
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
