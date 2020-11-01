package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.Success;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.repository.BaseJoinRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.services.BaseJoinServiceImpl;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/baseJoin")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseJoinController {

    private final BaseJoinServiceImpl baseJoinService;
    private final BaseJoinRepository baseJoinRepository;
    private final BaseRepository baseRepository;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public Single<BaseJoinListRes> getOwnBaseJoins(
            Authentication authentication,
            @QueryValue("isCreated") Optional<Boolean> isCreated) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> baseJoinService.getCreatedBaseJoins(
                        isCreated.isPresent() && isCreated.get(), authKey));
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public Single<HttpResponse<BaseJoin>> createBaseJoin(
            Authentication authentication,
            @Body BaseJoinReq baseJoinReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> baseJoinService
                        .createBaseJoin(authKey, baseJoinReq.getBaseId(),
                                baseJoinReq.getUserAuthKey(),
                                baseJoinReq.getBaseJoinAction()))
                .flatMap((baseJoin) -> Single.just(
                        HttpResponse.created(baseJoin)));
    }

    @Post("/{baseJoinId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<HttpResponse<BaseMember>> acceptBaseJoin(
            Authentication authentication,
            UUID baseJoinId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> baseJoinService.acceptBaseJoin(authKey,
                        baseJoinId))
                .flatMap((baseMember) -> Single.just(
                        HttpResponse.created(baseMember)));
    }

    @Delete("/{baseJoinId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<HttpResponse<Success>> declineBaseJoin(
            Authentication authentication,
            UUID baseJoinId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .zipWith(baseJoinRepository.findById(baseJoinId)
                                .toSingle(),
                        (authKey, baseJoin) -> {
                            if (baseJoin.getJoiningUser()
                                    .getAuthKey()
                                    .equals(authKey)
                                    || baseJoin.getCreator()
                                    .getAuthKey()
                                    .equals(authKey)) {
                                return baseJoinRepository.delete(baseJoin);
                            }
                            return baseRepository.findIfMemberAdmin(baseJoin.getBase()
                                    .getId(), authKey)
                                    .switchIfEmpty(Single.error(AuthenticationException::new))
                                    .flatMapCompletable(
                                            (base) -> baseJoinRepository.delete(baseJoin));
                        })
                .flatMap((baseMember) -> Single.just(HttpResponse.ok(new Success())));
    }
}
