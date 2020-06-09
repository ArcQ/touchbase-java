package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.Mission;
import com.kf.touchbase.models.dto.ProgressReq;
import com.kf.touchbase.services.postgres.MissionService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/mission/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MissionController {

    private final MissionService missionService;

    @Get("me")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Mission> getOwnMissions(Authentication authentication) {
        return missionService.findOwnMissions((String) authentication.getAttributes().get("username"));
    }

    @Post("progress")
    @Produces(MediaType.APPLICATION_JSON)
    public Mission progressMission(ProgressReq progressReq) {
        return null;
    }
}
