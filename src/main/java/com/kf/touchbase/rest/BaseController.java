package com.kf.touchbase.rest;

import com.kf.touchbase.exception.AuthorizationException;
import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.event.ChatEntityEventData;
import com.kf.touchbase.models.domain.event.EventAction;
import com.kf.touchbase.models.domain.event.ModifyMemberEventData;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.services.TouchbaseEventPublisher;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/base")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class BaseController {

    private final BaseRepository baseRepository;
    private final UserRepository userRepository;
    private final BaseMapper baseMapper;
    private final TouchbaseEventPublisher touchbaseEventPublisher;
    private final BaseMemberRepository baseMemberRepository;

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<ListRes<Base>> getOwnBases(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMap(baseRepository::findAllByMemberUserAuthKey)
                .toList()
                .flatMap(ListRes::toSingle);
    }

    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<HttpResponse<Base>> postBase(
            Authentication authentication,
            @Body BaseReq baseReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe(userRepository::findByAuthKey)
                .switchIfEmpty(Single.error(AuthenticationException::new))
                .flatMap((creator) -> {
                    var base = baseMapper.toEntity(baseReq);
                    base.setCreator(creator);
                    base.addMember(creator, Role.ADMIN);
                    touchbaseEventPublisher.publishEvent(new ChatEntityEventData(base));
                    return baseRepository.save(base);
                })
                .flatMap((savedBase) -> Single.just(HttpResponse.created(savedBase)));
    }

    @Get("/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Maybe<Base> getBaseIfOwn(Authentication authentication, UUID baseId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe((authKey) -> baseRepository.findIfMember(baseId, authKey));
    }

    @Delete("/{baseId}/member/me")
    @Operation(summary = "Leave a base",
            description = "must be a member of base")
    @Produces(MediaType.APPLICATION_JSON)
    public Completable leaveBase(Authentication authentication, UUID baseId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe((authKey) -> baseMemberRepository.findByUserAuthKeyAndBaseId(authKey,
                        baseId))
                .switchIfEmpty(Single.error(AuthorizationException::new))
                .doOnSuccess((member) -> touchbaseEventPublisher.publishEvent(new ModifyMemberEventData(member, EventAction.DELETE)))
                .flatMapCompletable(baseMemberRepository::delete);
    }

    @Delete("/{baseId}/member/{memberAuthKey}")
    @Operation(summary = "Kick a member out of a base",
            description = "must be one of the base admins")
    @Produces(MediaType.APPLICATION_JSON)
    public Completable kickMemberOut(
            Authentication authentication,
            UUID baseId,
            String memberAuthKey) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe(
                        (authKey) -> baseMemberRepository.findByUserAuthKeyAndBaseIdAndRole(authKey,
                                baseId, Role.ADMIN))
                .switchIfEmpty(Single.error(AuthorizationException::new))
                .zipWith(baseMemberRepository.findByUserAuthKeyAndBaseId(memberAuthKey, baseId)
                                .switchIfEmpty(Single.error(IllegalArgumentException::new)),
                        (admin, member) -> member)
                .doOnSuccess((member) -> touchbaseEventPublisher.publishEvent(new ModifyMemberEventData(member, EventAction.DELETE)))
                .flatMapCompletable(baseMemberRepository::delete);
    }

    //    @Patch("/{baseId}")
    //    @Produces(MediaType.APPLICATION_JSON)
    //    @ExecuteOn(TaskExecutors.IO)
    //    public Base patchBase(UUID baseId, @Body BaseReq baseReq) {
    //        var adminKey = SecurityUtils.getCurrentAuthKey();
    //        var base = baseMapper.toEntity(baseReq);
    //        var existingBase = findIfMemberAdmin(adminKey, baseId);
    //        existingBase.mergeInNotNull(base);
    //        return baseRepository.save(existingBase);
    //    }
    //
    //
    //    @Delete("/{baseId}")
    //    @Produces(MediaType.APPLICATION_JSON)
    //    @NotYetImplemented
    //    @Operation(description = "Not Implemented Yet")
    //    @ExecuteOn(TaskExecutors.IO)
    //    public Success makeBaseInactive(UUID baseId) {
    //        var adminKey = SecurityUtils.getCurrentAuthKey();
    //        var existingBase = findIfMemberAdmin(adminKey, baseId);
    //        existingBase.setActive(false);
    //        baseRepository.save(existingBase);
    //        return new Success();
    //    }
    //
    //    private Base findIfMemberAdmin(String adminAuthKey, UUID baseId) {
    //        return baseRepository.findIfMemberAdmin(baseId, adminAuthKey)
    //                .orElseThrow(() -> new SecurityException(String.format("%s not allowed",
    //                        adminAuthKey)));
    //    }
}
