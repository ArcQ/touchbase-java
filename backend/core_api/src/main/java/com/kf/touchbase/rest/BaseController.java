package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.services.neo4j.BaseServiceImpl;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/base/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseController {

    private final BaseServiceImpl baseService;

    private final BaseMapper baseMapper;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Base> getBases() {
        return baseService.findAll();
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public Base postBase(Authentication authentication, @Body BaseReq baseReq) {
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.createBase(AuthUtils.getUsernameFromAuth(authentication), base);
    }

    @Put
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({"ROLE_ADMIN"})
    public Base putBase(@Body BaseReq baseReq) {
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.createOrUpdate(base);
    }

    @Put
    @Produces(MediaType.APPLICATION_JSON)
    public Base patchBase(Authentication authentication, @Body BaseReq baseReq) {
        var base = baseMapper.baseReqToBase(baseReq);
        return baseService.patchBase(AuthUtils.getUsernameFromAuth(authentication), base);
    }

    @Post("{baseId}/user/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Base addUserToBaseAsOwner(Authentication authentication, String baseId, String username) {
        return baseService.addUserToBaseAsOwner(AuthUtils.getUsernameFromAuth(authentication), username, baseId);
    }

    @Delete("{baseId}/user/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Base removeUserFromBaseAsOwner(Authentication authentication, String baseId, String username) {
        return baseService.deleteUserFromBaseAsOwner(AuthUtils.getUsernameFromAuth(authentication), username, baseId);
    }

    @Delete("{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Success makeBaseInactive(Authentication authentication, String baseId) {
        return baseService.makeBaseInactive(AuthUtils.getUsernameFromAuth(authentication), baseId);
    }
}
