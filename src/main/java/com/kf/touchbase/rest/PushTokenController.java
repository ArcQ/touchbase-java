package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.postgres.ExpoPushToken;
import com.kf.touchbase.models.dto.PushTokenReq;
import com.kf.touchbase.models.dto.SendPushReq;
import com.kf.touchbase.repository.ExpoPushTokenRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.services.PushServiceImpl;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Controller("/api/v1/push")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class PushTokenController {
    private static final Integer TWO_YEARS_IN_MONTHS = 24;
    private final ExpoPushTokenRepository expoPushTokenRepository;
    private final UserRepository userRepository;
    private final PushServiceImpl pushService;

    @Post("/send")
    public Single<HttpResponse> sendPush(
            @RequestBody @Valid SendPushReq sendPushReq,
            Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe(userRepository::findByAuthKey)
                .switchIfEmpty(Single.error(IllegalArgumentException::new))
                .flatMap((user) -> pushService.sendPush(
                        user,
                        sendPushReq.getBody()
                                .getTitle(),
                        sendPushReq.getBody()
                                .getBody())
                )
                .map((_pushTicketResponse) -> HttpResponse.accepted());
    }

    @Post("/token/")
    @Transactional
    public Single<ExpoPushToken> postToken(
            @RequestBody @Valid PushTokenReq pushTokenReq,
            Authentication authentication) {
        return AuthUtils.getAuthKeyFromAuthRx(authentication)
                .flatMapMaybe((authKey) -> expoPushTokenRepository.findByUserAuthKeyAndDeviceId(
                        authKey,
                        pushTokenReq.getDeviceId()))
                .switchIfEmpty(Single.just(ExpoPushToken.builder()
                        .deviceId(pushTokenReq.getDeviceId())
                        .isValid(true))
                        .zipWith(AuthUtils.getAuthKeyFromAuthRx(authentication)
                                        .flatMapMaybe(userRepository::findByAuthKey)
                                        .switchIfEmpty(Single.error(IllegalArgumentException::new)),
                                (expoTokenBuilder, user) ->
                                        expoTokenBuilder.user(user)
                                                .token(pushTokenReq.getToken())
                                                .expiresAt(LocalDateTime.now()
                                                        .plus(TWO_YEARS_IN_MONTHS,
                                                                ChronoUnit.MONTHS))
                                                .build()))
                .flatMap(expoPushTokenRepository::save);
    }

}
