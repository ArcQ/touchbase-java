package com.kf.touchbase.rest;

import com.kf.touchbase.annotation.NotYetImplemented;
import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.dto.ProgressReq;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Controller("/api/v1/mission")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MissionController {

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public List<Mission> getOwnActiveMissions(Authentication authentication) {
        return null;
    }

    @Post("/progress")
    @Produces(MediaType.APPLICATION_JSON)
    @NotYetImplemented
    @Operation(description = "Not Implemented Yet")
    public Mission progressMission(ProgressReq progressReq) {
        return null;
    }
}
