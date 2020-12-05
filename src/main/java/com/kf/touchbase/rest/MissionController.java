package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.models.dto.MissionReq;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/mission")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MissionController {

    MissionRepository missionRepository;
    BaseRepository baseRepository;

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<ListRes<Mission>> getOwnActiveMissions(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMap(missionRepository::findAllByUserAuthKey)
                .toList()
                .flatMap(ListRes::toSingle);
    }

    @Get("/base/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<ListRes<Mission>> getOwnActiveMissionsByBase(
            Authentication authentication,
            UUID baseId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMap((authKey) -> missionRepository.findAllByUserAuthKeyByBaseId(authKey,
                        baseId))
                .toList()
                .flatMap(ListRes::toSingle);
    }

    @Get("/base/{baseId}/question/random")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Get a question, with difficulty based off of base meta information," +
            " right now just selects randomly")
    public Single<Mission> getQuestionFromBase(
            Authentication authentication,
            UUID baseId) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMap((authKey) -> baseRepository.findIfMemberAdmin(baseId, authKey))
                .toList()
                .flatMap(ListRes::toSingle);
    }

    @Put("/{missionId}/progress")
    @Produces(MediaType.APPLICATION_JSON)
    public Flowable<Object> updateMission(
            Authentication authentication,
            UUID missionId,
            MissionReq missionReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMapMaybe((authKey) -> missionRepository.findById(missionId))
                .flatMapSingle((mission) -> {
                    if (!mission.getIsStarted()) {
                        mission.setStartedAt(LocalDateTime.now());
                        mission.setIsStarted(true);
                    }
                    mission.setProgress(mission.getProgress() + missionReq.getValue());
                    return missionRepository.save(mission);
                });
    }
}
