package com.kf.touchbase.services.postgres;

import com.kf.touchbase.models.domain.MemberRef;
import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.services.postgres.repository.BaseRepository;
import com.kf.touchbase.services.postgres.repository.UserRepository;
import io.micronaut.security.authentication.AuthenticationException;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class BaseService {
    private final BaseRepository baseRepository;
    private final UserRepository userRepository;

    public Base findIfMemberAdmin(String adminAuthId, UUID baseId) {
        return baseRepository.findIfMemberAdmin(baseId, adminAuthId)
                .orElseThrow(() -> new SecurityException(String.format("%s not allowed", adminAuthId)));
    }

    public List<Base> getOwnBases(String adminAuthId) {
        return baseRepository.findAllByMember(adminAuthId);
    }

    public Base getBase(String adminAuthId, UUID baseId) {
        return baseRepository.findIfMember(baseId, adminAuthId).orElse(null);
    }

    @Transactional
    public Base createBase(String adminAuthId, Base newBase) {
        var creator = userRepository.findByAuthId(adminAuthId).orElseThrow(AuthenticationException::new);
        newBase.setCreator(creator);
        newBase.setAdmins(Set.of(creator));
        newBase.addMember(creator, Role.ADMIN);
        return baseRepository.save(newBase);
    }

    public Base patchBase(String adminAuthId, UUID baseId, Base updateBase) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthId, baseId);
        existingBase.mergeInNotNull(updateBase);
        return baseRepository.save(existingBase);
    }

    public Base addMember(String adminAuthId, MemberRef memberRef, UUID baseId, Role role) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthId, baseId);
        User user = userRepository.findById(memberRef.getUserId()).orElseThrow();
        existingBase.addMember(user, role);
        baseRepository.save(existingBase);
        return existingBase;
    }

    public Base removeMembers(String adminAuthId, List<MemberRef> memberRefs,
                              UUID baseId) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthId, baseId);
        memberRefs.forEach((memberRef) -> {
            existingBase.removeMember(memberRef.getUserId());
        });
        baseRepository.save(existingBase);
        return existingBase;
    }

    public Success makeBaseInactive(String adminAuthId, UUID baseId) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthId, baseId);
        existingBase.setActive(false);
        baseRepository.save(existingBase);
        return null;
    }
}
