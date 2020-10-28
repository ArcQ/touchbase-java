package com.kf.touchbase.services;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.models.domain.postgres.*;
import com.kf.touchbase.models.dto.BaseJoinAcceptReq;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.repository.BaseJoinRepository;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.authentication.AuthenticationException;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class BaseMemberServiceImpl {

    private final BaseJoinRepository baseJoinRepository;
    private final BaseRepository baseRepository;
    private final BaseMemberRepository baseMemberRepository;
    private final UserRepository userRepository;

    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<BaseJoinListRes> getOwnBaseJoins(String requesterAuthKey) {
        return baseJoinRepository.findAllByUserAuthKeyAndBaseJoinAction(requesterAuthKey,
                BaseJoinAction.Invite)
                .toList()
                .zipWith(baseJoinRepository.findAllByUserAuthKeyAndBaseJoinAction(requesterAuthKey,
                        BaseJoinAction.Request).toList(),
                        BaseJoinListRes::new);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<BaseJoin> createBaseJoin(
            String requesterAuthKey,
            UUID baseId,
            UUID userId,
            BaseJoinAction baseJoinAction) {
        var baseSingle = (baseJoinAction.equals(BaseJoinAction.Invite))
                ? baseRepository.findIfMemberAdmin(baseId, requesterAuthKey)
                .switchIfEmpty(Single.error(AuthenticationException::new))
                : Single.just(Base.fromId(baseId));

        return baseSingle
                .flatMap((base) -> {
                    var baseJoin = new BaseJoin(base, User.fromId(userId), baseJoinAction);
                    return baseJoinRepository.save(baseJoin);
                });
    }

    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<BaseMember> acceptBaseJoin(String authKey, BaseJoinAcceptReq baseJoinAcceptReq) {
        return baseJoinRepository.findById(baseJoinAcceptReq.getBaseJoinId())
                .toSingle()
                .flatMap((baseJoin) -> {
                    if (baseJoin.getJoiningUser()
                            .getAuthKey()
                            .equals(authKey)) {
                        return Single.just(baseJoin);
                    }
                    throw new IllegalArgumentException("User could not be matched with " +
                            "base join");
                })
                .flatMap((baseJoin) -> {
                    baseJoinRepository.delete(baseJoin)
                            .subscribe();
                    return baseMemberRepository.save(
                            BaseMember.builder()
                                    .user(baseJoin.getJoiningUser())
                                    .base(Base.fromId(baseJoin.getBase()
                                            .getId()))
                                    .build()
                    );
                });
    }

    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Single<BaseMember> addMember(
            UUID baseId,
            String userAuthKey) {
        return baseMemberRepository.save(BaseMember.builder()
                .base(Base.fromId(baseId))
                .user(User.fromAuthKey(userAuthKey))
                .build());
    }

    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    @ExecuteOn(TaskExecutors.IO)
    public Completable removeMember(
            UUID baseId,
            String userAuthKey) {
        return baseMemberRepository
                .findByUserAuthKeyAndBaseId(userAuthKey, baseId)
                .flatMapCompletable(baseMemberRepository::delete);
    }

}
