package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.repository.BaseJoinRepository;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import io.micronaut.security.authentication.AuthenticationException;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Singleton
public class BaseMemberServiceImpl {

    private final BaseJoinRepository baseJoinRepository;
    private final BaseRepository baseRepository;
    private final BaseMemberRepository baseMemberRepository;
    private final UserRepository userRepository;

    public Single<BaseJoinListRes> getOwnBaseJoins(String requesterAuthKey) {
        return baseJoinRepository.findAllByUserAuthKeyAndBaseJoinAction(requesterAuthKey,
                BaseJoinAction.Request.name())
                .toList()
                .zipWith(baseJoinRepository.findAllByUserAuthKeyAndBaseJoinAction(requesterAuthKey,
                        BaseJoinAction.Invite.name())
                                .toList(),
                        BaseJoinListRes::new);
    }

    public Single<BaseJoin> createBaseJoin(
            String requesterAuthKey,
            UUID baseId,
            String userAuthKey,
            BaseJoinAction baseJoinAction) {
        var baseSingle = (baseJoinAction.equals(BaseJoinAction.Invite))
                ? baseRepository.findIfMemberAdmin(baseId, requesterAuthKey)
                .switchIfEmpty(Single.error(AuthenticationException::new))
                : Single.just(Base.fromId(baseId));

        return baseSingle
                .zipWith(userRepository.findByAuthKey(userAuthKey)
                        .toSingle(), Map::entry)
                .flatMap((entry) -> baseJoinRepository.save(
                        new BaseJoin(entry.getKey(), entry.getValue(), baseJoinAction)));
    }

    public Single<BaseMember> acceptBaseJoin(String authKey, UUID baseJoinId) {
        return baseJoinRepository.findById(baseJoinId)
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
}
