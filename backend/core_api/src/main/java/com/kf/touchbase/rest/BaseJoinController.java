package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.mappers.BaseJoinMapper;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import com.kf.touchbase.models.domain.postgres.Success;
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
@Controller("/api/v1/baseJoin")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseJoinController {

    @Named("BaseInviteService")
    private final BaseJoinService baseInviteService;

    @Named("BaseRequestService")
    private final BaseJoinService baseRequestService;

    private final BaseJoinMapper baseJoinMapper;

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Iterable<BaseJoin> getBases(Authentication authentication) {
        return baseInviteService.findAllByOwner(AuthUtils.getUserIdFromAuth(authentication));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public BaseJoin createBaseJoin(Authentication authentication, @Body BaseJoinReq baseJoinReq) {
        var baseJoin = baseJoinMapper.basejoinReqToRequest(baseJoinReq);
        if (baseJoin.getBaseJoinAction().equals(BaseJoinAction.Invite)) {
            return baseInviteService.createBaseJoin(AuthUtils.getUserIdFromAuth(authentication),
                    baseJoin);
        }
        return baseRequestService.createBaseJoin(AuthUtils.getUserIdFromAuth(authentication),
                baseJoin);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public BaseJoin requestIntoBase(Authentication authentication, @Body BaseJoinReq baseJoinReq) {
        var baseJoin = baseJoinMapper.basejoinReqToRequest(baseJoinReq);
        if (baseJoin.getBaseJoinAction().equals(BaseJoinAction.Invite)) {
            return baseInviteService.createBaseJoin(AuthUtils.getUserIdFromAuth(authentication),
                    baseJoin);
        }
        return baseRequestService.createBaseJoin(AuthUtils.getUserIdFromAuth(authentication),
                baseJoin);
    }

    @Delete("{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Success deleteBaseInvite(Authentication authentication, String baseId) {
        return baseInviteService.deleteBaseJoin(AuthUtils.getUserIdFromAuth(authentication),
                baseId);
    }
}
