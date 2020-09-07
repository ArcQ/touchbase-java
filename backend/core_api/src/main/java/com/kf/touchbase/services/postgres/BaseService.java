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

    public Base findIfMemberAdmin(String adminAuthKey, UUID baseId) {
        return baseRepository.findIfMemberAdmin(baseId, adminAuthKey)
                .orElseThrow(() -> new SecurityException(String.format("%s not allowed", adminAuthKey)));
    }

    public List<Base> getOwnBases(String adminAuthKey) {
        var bases = baseRepository.findAllByMembersUserAuthKey(adminAuthKey);
        return bases;
    }

    public Base getBase(String adminAuthKey, UUID baseId) {
        return baseRepository.findIfMember(baseId, adminAuthKey).orElse(null);
    }

    @Transactional
    public Base createBase(String adminAuthKey, Base newBase) {
        var creator = userRepository.findByAuthKey(adminAuthKey).orElseThrow(AuthenticationException::new);
        newBase.setCreator(creator);
        newBase.setAdmins(Set.of(creator));
        newBase.addMember(creator, Role.ADMIN);
        return baseRepository.save(newBase);
    }

    public Base patchBase(String adminAuthKey, UUID baseId, Base updateBase) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        existingBase.mergeInNotNull(updateBase);
        return baseRepository.save(existingBase);
    }

    public Base addMember(String adminAuthKey, MemberRef memberRef, UUID baseId, Role role) throws SecurityException {
        Base existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        User user = userRepository.findById(memberRef.getUserId()).orElseThrow();
        existingBase.addMember(user, role);
        baseRepository.save(existingBase);
        return existingBase;
    }

    public Base removeMembers(String adminAuthKey, List<MemberRef> memberRefs,
                              UUID baseId) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        memberRefs.forEach((memberRef) -> {
            existingBase.removeMember(memberRef.getUserId());
        });
        baseRepository.save(existingBase);
        return existingBase;
    }

    public Success makeBaseInactive(String adminAuthKey, UUID baseId) throws SecurityException {
        var existingBase = findIfMemberAdmin(adminAuthKey, baseId);
        existingBase.setActive(false);
        baseRepository.save(existingBase);
        return null;
    }
}
