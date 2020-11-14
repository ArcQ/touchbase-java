package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.NewsArticle;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;

@RequiredArgsConstructor
@Singleton
public class NewsServiceImpl {

    @Inject
    @Client("/")
    private final RxHttpClient client;

    public Flowable<NewsArticle> getArticlesFromTopic(String topic) {
        return null;
    }
}
