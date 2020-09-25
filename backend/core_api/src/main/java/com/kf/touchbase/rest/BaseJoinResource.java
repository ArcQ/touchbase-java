package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.mappers.BaseJoinMapper;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.services.postgres.BaseJoinService;
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

import javax.inject.Named;
import java.util.List;

@RequiredArgsConstructor
@Controller("/api/v1/baseJoin")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseJoinResource {

    @Named("BaseInviteService")
    private final BaseJoinService baseInviteService;

    @Named("BaseRequestService")
    private final BaseJoinService baseRequestService;

    private final BaseJoinMapper baseJoinMapper;

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public List<BaseJoin> getBases(Authentication authentication) {
        return baseInviteService.findByAdmin(AuthUtils.getAuthKeyFromAuth(authentication));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public BaseJoin createBaseJoin(Authentication authentication, @Body BaseJoinReq baseJoinReq) {
        var baseJoin = baseJoinMapper.toEntity(baseJoinReq);
        if (baseJoin.getBaseJoinAction().equals(BaseJoinAction.Invite)) {
            return baseInviteService.createBaseJoin(AuthUtils.getAuthKeyFromAuth(authentication),
                    baseJoin);
        }
        return baseRequestService.createBaseJoin(AuthUtils.getAuthKeyFromAuth(authentication),
                baseJoin);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public BaseJoin requestIntoBase(Authentication authentication, @Body BaseJoinReq baseJoinReq) {
        var baseJoin = baseJoinMapper.toEntity(baseJoinReq);
        if (baseJoin.getBaseJoinAction().equals(BaseJoinAction.Invite)) {
            return baseInviteService.createBaseJoin(AuthUtils.getAuthKeyFromAuth(authentication),
                    baseJoin);
        }
        return baseRequestService.createBaseJoin(AuthUtils.getAuthKeyFromAuth(authentication),
                baseJoin);
    }

    @Delete("{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Success deleteBaseInvite(Authentication authentication, String baseId) {
        return baseInviteService.deleteBaseJoin(AuthUtils.getAuthKeyFromAuth(authentication),
                baseId);
    }
}
