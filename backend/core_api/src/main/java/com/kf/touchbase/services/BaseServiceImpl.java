package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.domain.Person;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.TouchBaseDomain;
import com.kf.touchbase.utils.TouchbaseBeanUtils;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.UUID;

@Singleton
public class BaseServiceImpl extends AbstractEntityDataService<Base> implements BaseService {
    private PersonService personService;

    public BaseServiceImpl(SessionFactory sessionFactory,
                           PersonService personService) {
        super(sessionFactory);
        this.personService = personService;
    }

    @Override
    public Base createBase(String creatorUsername, Base newBase) {
        var creator = personService.getByUsername(creatorUsername);
        newBase.setCreator(creator);
        newBase.setOwner(creator);
        newBase.setMembers(Collections.singleton(creator));
        createOrUpdate(newBase);
        return null;
    }

    @Override
    public Base patchBase(String ownerUserName, Base updateBase) throws SecurityException {
        Base existingBase = findIfOwner(ownerUserName, updateBase);
        TouchbaseBeanUtils.mergeInNotNull(
                existingBase, updateBase, TouchBaseDomain.class, "email", "name", "score", "imageUrl",
                "owner");
        createOrUpdate(existingBase);
        return null;
    }

    @Override
    public Base addUserToBaseAsOwner(String ownerUsername, String addUsername, String baseId) throws SecurityException {
        Base existingBase = findIfOwnerId(ownerUsername, UUID.fromString(baseId), Base.class);
        Person person = personService.getByUsername(addUsername);
        existingBase.getMembers().add(person);
        createOrUpdate(existingBase);
        return null;
    }

    @Override
    public Base deleteUserFromBaseAsOwner(String ownerUsername, String addUsername, String baseId) throws SecurityException {
        Base existingBase = findIfOwnerId(ownerUsername, UUID.fromString(baseId), Base.class);
        Person person = personService.getByUsername(addUsername);
        existingBase.getMembers().remove(person);
        createOrUpdate(existingBase);
        return null;
    }

    @Override
    public Success makeBaseInactive(String ownerUsername, String baseId) throws SecurityException {
        Base existingBase = findIfOwnerId(ownerUsername, UUID.fromString(baseId), Base.class);
        existingBase.setActive(false);
        createOrUpdate(existingBase);
        return null;
    }

    @Override
    public Class<Base> getEntityType() {
        return Base.class;
    }
}
