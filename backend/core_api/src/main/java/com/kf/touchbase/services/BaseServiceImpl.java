package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.domain.TouchBaseDomain;
import com.kf.touchbase.utils.TouchbaseBeanUtils;
import org.neo4j.ogm.session.SessionFactory;

import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class BaseServiceImpl extends AbstractEntityDataService<Base> implements BaseService {
    private PersonService personService;

    public BaseServiceImpl(SessionFactory sessionFactory,
                           PersonService personService) {
        super(sessionFactory);
        this.personService = personService;
    }

    public Base createBase(String creatorUsername, Base newBase) {
        var creator = personService.getByUsername(creatorUsername);
        newBase.setCreator(creator);
        newBase.setOwner(creator);
        newBase.setMembers(Collections.singleton(creator));
        createOrUpdate(newBase);
        return null;
    }

    public Base patchBase(String username, Base updateBase) {
        Base existingBase = findIfOwner(username, updateBase);
        TouchbaseBeanUtils.mergeInNotNull(
                existingBase, updateBase, TouchBaseDomain.class, "email", "name", "score", "imageUrl",
                "owner");
        createOrUpdate(existingBase);
        return null;
    }

    @Override
    public Iterable<Base> findAll() {
        var session = sessionFactory.openSession();
        return session.loadAll(Base.class, 1);
    }

    @Override
    public Class<Base> getEntityType() {
        return Base.class;
    }
}
