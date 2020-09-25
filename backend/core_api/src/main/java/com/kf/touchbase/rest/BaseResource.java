package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.mappers.BaseMemberMapper;
import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.ListReq;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.models.dto.MemberReq;
import com.kf.touchbase.services.postgres.repository.BaseMemberRepository;
import com.kf.touchbase.services.postgres.repository.BaseRepository;
import com.kf.touchbase.services.postgres.repository.UserRepository;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/bases")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseResource {

    private final BaseRepository baseRepository;
    private final UserRepository userRepository;
    private final BaseMemberRepository baseMemberRepository;

    private final BaseMapper baseMapper;
    private final BaseMemberMapper baseMemberMapper;

    @Get("/")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
    public ListRes<Base> getOwnBases(Authentication authentication) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        var bases = baseRepository.findAllByMembersUserAuthKey(adminKey);
        return new ListRes<>(bases);
    }

    @Post("/")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
    public Base postBase(Authentication authentication, @Body BaseReq baseReq) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        var base = baseMapper.toEntity(baseReq);
        var creator =
                userRepository.findByAuthKey(adminKey).orElseThrow(AuthenticationException::new);
        base.setCreator(creator);
        base.setAdmins(Set.of(creator));
        base.addMember(creator, Role.ADMIN);
        return baseRepository.save(base);
    }

    @Get("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Base getBaseIfOwn(Authentication authentication, UUID baseId) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        return baseRepository.findIfMember(baseId, adminKey).orElse(null);
    }

    @Patch("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Base patchBase(Authentication authentication, UUID baseId, @Body BaseReq baseReq) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        var base = baseMapper.toEntity(baseReq);
        var existingBase = findIfMemberAdmin(adminKey, baseId);
        existingBase.mergeInNotNull(base);
        return baseRepository.save(existingBase);
    }

    @Delete("/{baseId}/members")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Base addMembers(Authentication authentication, UUID baseId,
                              @Body ListReq<MemberReq> userIds) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        var existingBase = findIfMemberAdmin(adminKey, baseId);
        return existingBase;
    }

    @Delete("/{baseId}/members")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Base removeMembers(Authentication authentication, UUID baseId,
                              @Body ListReq<UUID> userIds) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        var existingBase = findIfMemberAdmin(adminKey, baseId);
        userIds.getList().forEach(existingBase::removeMember);
        baseRepository.save(existingBase);
        return existingBase;
    }

    @Delete("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Success makeBaseInactive(Authentication authentication, UUID baseId) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
        var existingBase = findIfMemberAdmin(adminKey, baseId);
        existingBase.setActive(false);
        baseRepository.save(existingBase);
        return new Success();
    }

    private Base findIfMemberAdmin(String adminAuthKey, UUID baseId) {
        return baseRepository.findIfMemberAdmin(baseId, adminAuthKey)
                .orElseThrow(() -> new SecurityException(String.format("%s not allowed", adminAuthKey)));
    }
}
