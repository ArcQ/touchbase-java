package com.kf.touchbase.rest;

import com.agorapulse.micronaut.aws.s3.SimpleStorageService;
import com.kf.touchbase.models.dto.SignedUrlRes;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Controller("/api/v1/upload")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UploadController {

    private final SimpleStorageService simpleStorageService;

    private Date getTommorrow() {
        var dt = new Date();
        var calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    private SignedUrlRes generatePresignedUrl(Optional<String> key) {
        var presignedUrl = simpleStorageService.generatePresignedUrl(
                key.orElseThrow(() -> new IllegalArgumentException("File Key Required")),
                getTommorrow());
        return new SignedUrlRes(presignedUrl);
    }

    @Get("/signed-url{?key}")
    @ExecuteOn(TaskExecutors.IO)
    @Produces(MediaType.APPLICATION_JSON)
    public Single<SignedUrlRes> upload(Authentication authentication, Optional<String> key) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMap((authKey) -> Single.fromCallable(() -> generatePresignedUrl(key)));
    }
}
