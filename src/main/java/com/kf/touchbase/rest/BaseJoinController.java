package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.repository.BaseJoinRepository;
import com.kf.touchbase.services.BaseMemberServiceImpl;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/baseJoin")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseJoinController {

    private final BaseMemberServiceImpl baseMemberService;
    private final BaseJoinRepository baseJoinRepository;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Single<BaseJoinListRes> getOwnBaseJoins(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap(baseMemberService::getOwnBaseJoins);
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Single<HttpResponse<BaseJoin>> createBaseJoin(
            Authentication authentication,
            @Body BaseJoinReq baseJoinReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> baseMemberService
                        .createBaseJoin(authKey, baseJoinReq.getBaseId(),
                                baseJoinReq.getUserAuthKey(),
                                baseJoinReq.getBaseJoinAction()))
                .flatMap((baseJoin) -> Single.just(
                        HttpResponse.created(baseJoin)));
    }

    @Post("/{baseJoinId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Single<HttpResponse<BaseMember>> acceptBaseJoin(
            Authentication authentication,
            UUID baseJoinId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> baseMemberService.acceptBaseJoin(authKey,
                        baseJoinId))
                .flatMap((baseMember) -> Single.just(
                        HttpResponse.created(baseMember)));
    }

    @Delete("/{baseJoinId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public Single<HttpResponse<Success>> declineBaseJoin(
            Authentication authentication,
            UUID baseJoinId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .zipWith(baseJoinRepository.findById(baseJoinId)
                                .toSingle(),
                        (authKey, baseJoin) -> {
                            if (baseJoin.getJoiningUser()
                                    .getAuthKey()
                                    .equals(authKey) || baseJoin.getCreator()
                                    .getAuthKey()
                                    .equals(authKey)) {
                                return baseJoinRepository.delete(baseJoin);
                            }
                            throw new AuthenticationException(
                                    "User not allowed to decline base " +
                                            "join");
                        })
                .flatMap((baseMember) -> Single.just( HttpResponse.ok(new Success())));
    }
}
