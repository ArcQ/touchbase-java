package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.models.domain.neo4j.Base;
import com.kf.touchbase.models.domain.postgres.Success;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.services.neo4j.BaseService;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
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

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public Base postBase(Authentication authentication, @Body BaseReq baseReq) {
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.createBase(AuthUtils.getUserIdFromAuth(authentication), base);
    }

    @Patch("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Base patchBase(Authentication authentication, String baseId, @Body BaseReq baseReq) {
        UUID baseUuid = UUID.fromString(baseId);
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.patchBase(AuthUtils.getUserIdFromAuth(authentication), baseUuid, base);
    }

    @Post("/{baseId}/owner/{authId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Base addUserToBaseAsOwner(Authentication authentication, String baseId, String authId) {
        return baseService.addUserToBaseAsOwner(AuthUtils.getUserIdFromAuth(authentication), authId, baseId);
    }

    @Patch("/{baseId}/owner/{authId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Base patchUserToBaseAsOwner(Authentication authentication, String baseId, String authId, @Body BaseReq baseReq) {
        UUID baseUuid = UUID.fromString(baseId);
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.patchBase(AuthUtils.getUserIdFromAuth(authentication), baseUuid, base);
    }

    @Delete("/{baseId}/owner/{authId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Base removeUserFromBaseAsOwner(Authentication authentication, String baseId, String authId) {
        return baseService.deleteUserFromBaseAsOwner(AuthUtils.getUserIdFromAuth(authentication), authId, baseId);
    }

    @Delete("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Success makeBaseInactive(Authentication authentication, UUID baseId) {
        return baseService.makeBaseInactive(AuthUtils.getUserIdFromAuth(authentication), baseId);
    }
}
