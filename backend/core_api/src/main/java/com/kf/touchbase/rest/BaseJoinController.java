package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.BaseJoinMapper;
import com.kf.touchbase.models.domain.BaseJoin;
import com.kf.touchbase.models.domain.BaseJoinAction;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.services.postgres.BaseJoinService;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@RequiredArgsConstructor
@Controller("/api/v1/baseJoin/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseJoinController {

    @Named("BaseInviteService")
    private final BaseJoinService baseInviteService;

    @Named("BaseRequestService")
    private final BaseJoinService baseRequestService;

    private final BaseJoinMapper baseJoinMapper;

    @Get("me")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all the joins and requests you've made")
    public Iterable<BaseJoin> getBases(Authentication authentication) {
        return baseInviteService.findAllByOwner((String) authentication.getAttributes().get(
                "username"));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public BaseJoin createBaseJoin(Authentication authentication, @Body BaseJoinReq baseJoinReq) {
        var baseJoin = baseJoinMapper.basejoinReqToRequest(baseJoinReq);
        if (baseJoin.getBaseJoinAction().equals(BaseJoinAction.Invite)) {
            return baseInviteService.createBaseJoin(AuthUtils.getUsernameFromAuth(authentication),
                    baseJoin);
        }
        return baseRequestService.createBaseJoin(AuthUtils.getUsernameFromAuth(authentication),
                baseJoin);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public BaseJoin requestIntoBase(Authentication authentication, @Body BaseJoinReq baseJoinReq) {
        var baseJoin = baseJoinMapper.basejoinReqToRequest(baseJoinReq);
        if (baseJoin.getBaseJoinAction().equals(BaseJoinAction.Invite)) {
            return baseInviteService.createBaseJoin(AuthUtils.getUsernameFromAuth(authentication),
                    baseJoin);
        }
        return baseRequestService.createBaseJoin(AuthUtils.getUsernameFromAuth(authentication),
                baseJoin);
    }

    @Delete("{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Success makeBaseInactive(Authentication authentication, String baseId) {
        return baseInviteService.deleteBaseJoin(AuthUtils.getUsernameFromAuth(authentication),
                baseId);
    }
}
