package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.ExpoPushToken;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.repository.ExpoPushTokenRepository;
import com.kinoroy.expo.push.ExpoPushClient;
import com.kinoroy.expo.push.Message;
import com.kinoroy.expo.push.PushTicketResponse;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PushServiceImpl {
    private final ExpoPushTokenRepository expoPushTokenRepository;

    public Single<PushTicketResponse> sendPush(User user, String title, String body) {
        return expoPushTokenRepository.findAllByUserAuthKey(user.getAuthKey())
                .map(ExpoPushToken::getToken)
                .map((userToken) -> new Message.Builder()
                        .badge(1)
                        .title(title)
                        .body(body)
                        .data(new HashMap<>(Map.of("name", user.getUsername())))
                        .build())
                .toList()
                .map(ExpoPushClient::sendPushNotifications);
    }

    public Single<Integer> sendAll(String title, String body) {
        return expoPushTokenRepository.findAll()
                .map(ExpoPushToken::getToken)
                .map((userToken) -> new Message.Builder()
                        .badge(1)
                        .title(title)
                        .body(body)
                        .build())
                .toList()
                .map((pushMessages) -> {
                    var chunked = ExpoPushClient.chunkItems(pushMessages);
                    var numberOfMessage = 0;
                    for (var chunk : chunked) {
                        try {
                            numberOfMessage += chunk.size();
                            ExpoPushClient.sendPushNotifications(chunk);
                        } catch (Exception e) {
                            log.error("error sending pushes to agen");
                        }
                    }
                    return numberOfMessage;
                });
    }
}
