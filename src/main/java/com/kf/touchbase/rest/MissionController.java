package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.models.dto.ProgressReq;
import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Controller("/api/v1/mission")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MissionController {

    MissionRepository missionRepository;

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Single<ListRes<Mission>> getOwnActiveMissions(Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMap(missionRepository::findAllByUserAuthKey)
                .toList()
                .flatMap(ListRes::toSingle);
    }

    @Get("/base/{baseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
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

    @Post("/progress")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Single<ListRes<Mission>> progressMission(
            Authentication authentication,
            ProgressReq progressReq) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .toFlowable()
                .flatMap((authKey) -> missionRepository.findAllByUserAuthKeyByBaseId(authKey,
                        progressReq.getBaseId()))
                .map((mission) -> {
                    mission.setProgress(progressReq.getValue());
                    return mission;
                })
                .toList()
                .flatMap(ListRes::toSingle);
    }
}
