package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.ListReq;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.models.dto.MemberReq;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.utils.AuthUtils;
import com.kf.touchbase.utils.SecurityUtils;
import io.micronaut.http.HttpResponse;
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

import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/bases")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseResource {

    private final BaseRepository baseRepository;
    private final UserRepository userRepository;
    private final BaseMapper baseMapper;

    @Get("/")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
    public ListRes<Base> getOwnBases() {
        var adminKey = SecurityUtils.getCurrentAuthKey();
        var bases = baseRepository.findAllByMembersUserAuthKey(adminKey);
        return new ListRes<>(bases);
    }

    @Post("/")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
//    @Transactional
    public HttpResponse<Base> postBase(Authentication authentication, @Body BaseReq baseReq) {
        var adminKey = AuthUtils.getAuthKeyFromAuth(authentication);
//        var adminKey = SecurityUtils.getCurrentAuthKey();
        var base = baseMapper.toEntity(baseReq);
        var creator =
                userRepository.findByAuthKey(adminKey).orElseThrow(AuthenticationException::new);
        base.setCreator(creator);
//        var savedBase = baseRepository.save(base);
//        savedBase.addMember(creator, Role.ADMIN);
        base.addMember(creator, Role.ADMIN);
        return HttpResponse.created(baseRepository.save(base));
    }

    @Get("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Base getBaseIfOwn(UUID baseId) {
        var adminKey = SecurityUtils.getCurrentAuthKey();
        return baseRepository.findIfMember(baseId, adminKey).orElse(null);
    }

    @Patch("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Base patchBase(UUID baseId, @Body BaseReq baseReq) {
        var adminKey = SecurityUtils.getCurrentAuthKey();
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
    public Base addMembers(UUID baseId,
                              @Body ListReq<MemberReq> userIds) {
        var adminKey = SecurityUtils.getCurrentAuthKey();
        var existingBase = findIfMemberAdmin(adminKey, baseId);
        return existingBase;
    }

    @Delete("/{baseId}/members")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Base removeMembers(UUID baseId,
                              @Body ListReq<UUID> userIds) {
        var adminKey = SecurityUtils.getCurrentAuthKey();
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
    public Success makeBaseInactive(UUID baseId) {
        var adminKey = SecurityUtils.getCurrentAuthKey();
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
