package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.User;
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
    private final UserService userService;
    private final BaseRepository baseRepository;

    public Base createBase(String adminAuthId, Base newBase) {
        var creator = userService.findByAuthId(adminAuthId);
        newBase.setCreator(creator);
        newBase.setAdmins(Set.of(creator));
        newBase.setMembers(Collections.singleton(creator));
        return baseRepository.save(newBase);
    }

    public Base patchBase(String adminAuthId, UUID baseId, Base updateBase) throws SecurityException {
        Base existingBase = baseRepository.findifAdminId(adminAuthId, baseId, Base.class);
        existingBase.mergeInNotNull(updateBase);
        return baseRepository.save(existingBase);
    }

    public Base addUserToBaseAsAdmin(String adminAuthId, String addAuthId, String baseId) throws SecurityException {
        Base existingBase = baseRepository.findifAdminId(adminAuthId, UUID.fromString(baseId),
                Base.class);
        User user = userService.findByAuthId(addAuthId);
        existingBase.getMembers().add(user);
        baseRepository.save(existingBase);
        return null;
    }

    public Base deleteUserFromBaseAsAdmin(String adminAuthId, String removeAuthId,
                                          String baseId) throws SecurityException {
        Base existingBase = baseRepository.findifAdminId(adminAuthId, UUID.fromString(baseId),
                Base.class);
        existingBase.getMembers().removeIf((user) -> user.getAuthId().equals(removeAuthId));
        baseRepository.save(existingBase);
        return null;
    }

    public Success makeBaseInactive(String adminAuthId, UUID baseId) throws SecurityException {
        Base existingBase = baseRepository.findifAdminId(adminAuthId, baseId, Base.class);
        existingBase.setActive(false);
        baseRepository.save(existingBase);
        return null;
    }
}
