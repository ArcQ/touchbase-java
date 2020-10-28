package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.BaseJoinAcceptReq;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.services.BaseMemberServiceImpl;
import com.kf.touchbase.services.basejoin.BaseJoinService;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/baseJoin")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseJoinController {

    @Named("BaseInviteService")
    private final BaseJoinService baseInviteService;

    private final BaseMemberServiceImpl baseMemberService;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<BaseJoinListRes> getOwnBaseJoins(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap(baseMemberService::getOwnBaseJoins);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<HttpResponse<BaseJoin>> createBaseJoin(
            Authentication authentication,
            @Body BaseJoinReq baseJoinReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> baseMemberService
                        .createBaseJoin(authKey, UUID.fromString(baseJoinReq.getBaseId()),
                                UUID.fromString(baseJoinReq.getUserId()),
                                baseJoinReq.getBaseJoinAction()))
                .flatMap((baseJoin) -> Single.just(
                        HttpResponse.created(baseJoin)));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<HttpResponse<BaseMember>> acceptBaseJoin(
            Authentication authentication,
            @Body BaseJoinAcceptReq baseJoinAcceptReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .zipWith(Single.just(baseJoinAcceptReq), Map::entry)
                .flatMap((entry) -> baseMemberService.acceptBaseJoin(entry.getKey(),
                        entry.getValue()))
                .flatMap((baseMember) -> Single.just(
                        HttpResponse.created(baseMember)));
    }

    @Delete("{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Success declineBaseJoin(
            Authentication authentication,
            String baseId) {
        return baseInviteService.deleteBaseJoin(AuthUtils.getAuthKeyFromAuth(authentication),
                baseId);
    }
}
