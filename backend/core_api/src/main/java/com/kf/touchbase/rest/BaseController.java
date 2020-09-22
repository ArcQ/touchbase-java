package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.ListReq;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.models.dto.MemberReq;
import com.kf.touchbase.services.postgres.BaseService;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/base")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseController {

    private final BaseService baseService;

    private final BaseMapper baseMapper;

    @Get("/")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
    public ListRes<Base> getOwnBases(Authentication authentication) {
        return new ListRes<>(baseService.getOwnBases(AuthUtils.getAuthKeyFromAuth(authentication)));
    }

    @Post("/")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
    public Base postBase(Authentication authentication, @Body BaseReq baseReq) {
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.createBase(AuthUtils.getAuthKeyFromAuth(authentication), base);
    }

    @Get("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Base getBaseIfOwn(Authentication authentication, UUID baseId) {
        return baseService.getBase(AuthUtils.getAuthKeyFromAuth(authentication), baseId);
    }

    @Patch("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Base patchBase(Authentication authentication, UUID baseId, @Body BaseReq baseReq) {
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.patchBase(AuthUtils.getAuthKeyFromAuth(authentication), baseId, base);
    }

    @Delete("/{baseId}/members")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Base removeMembers(Authentication authentication, UUID baseId, @Body ListReq<MemberReq> memberReqs) {
        var memberRefs = baseMapper.memberReqsToMemberRefs(memberReqs.getList());
        return baseService.removeMembers(AuthUtils.getAuthKeyFromAuth(authentication), memberRefs, baseId);
    }

    @Delete("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Success makeBaseInactive(Authentication authentication, UUID baseId) {
        return baseService.makeBaseInactive(AuthUtils.getAuthKeyFromAuth(authentication), baseId);
    }
}
