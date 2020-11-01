package com.kf.touchbase.services;

import com.kf.touchbase.exception.AuthorizationException;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.repository.*;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class BaseJoinServiceImpl {

    private final BaseJoinRepository baseJoinRepository;
    private final BaseRepository baseRepository;
    private final BaseMemberRepository baseMemberRepository;
    private final UserRepository userRepository;

    public Single<BaseJoinListRes> getCreatedBaseJoins(Boolean isCreated, String authKey) {
        var requests = isCreated ? baseJoinRepository.findMyCreatedRequests(authKey) :
                baseJoinRepository.findRequestsToMyBases(authKey);
        var invites = isCreated ? baseJoinRepository.findMyCreatedInvites(authKey) :
                baseJoinRepository.findInvitesToMe(authKey);
        return requests.toList().zipWith(invites.toList(), BaseJoinListRes::new);
    }

    public Single<BaseJoin> createBaseJoin(
            String requesterAuthKey,
            UUID baseId,
            String userAuthKey,
            BaseJoinAction baseJoinAction) {
        var baseSingle = (baseJoinAction.equals(BaseJoinAction.INVITE))
                ? baseRepository.findIfMemberAdmin(baseId, requesterAuthKey)
                .switchIfEmpty(Single.error(AuthorizationException::new))
                : Single.just(Base.fromId(baseId));

        return baseSingle
                .zipWith(userRepository.findByAuthKey(userAuthKey)
                        .toSingle(), Map::entry)
                .flatMap((entry) -> baseJoinRepository.save(
                        new BaseJoin(entry.getKey(), entry.getValue(), baseJoinAction)));
    }

    public Single<BaseMember> acceptBaseJoin(String accepterAuthKey, UUID baseJoinId) {
        return baseJoinRepository.findById(baseJoinId)
                .toSingle()
                .flatMap((baseJoin) -> {
                    if (baseJoin.getBaseJoinAction()
                            .equals(BaseJoinAction.INVITE) && baseJoin.getJoiningUser()
                            .getAuthKey()
                            .equals(accepterAuthKey)) {
                        return Single.just(baseJoin);
                    }
                    if (baseJoin.getBaseJoinAction()
                            .equals(BaseJoinAction.REQUEST)) {
                        return baseRepository.findIfMemberAdmin(baseJoin.getBase()
                                        .getId(),
                                accepterAuthKey)
                                .switchIfEmpty(Single.error(AuthorizationException::new))
                                .flatMap((base) -> Single.just(baseJoin));
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
}
