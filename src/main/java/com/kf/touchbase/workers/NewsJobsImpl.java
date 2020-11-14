package com.kf.touchbase.workers;

import com.kf.touchbase.models.domain.postgres.MissionTemplate;
import com.kf.touchbase.models.domain.postgres.NewsArticle;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.repository.NewsRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class NewsJobsImpl {

    NewsRepository newsRepository;

    @Inject
    @Client("/")
    RxHttpClient client;

    private List<MissionTemplate> generateMissionTemplates(User user) {
        return Collections.singletonList(new MissionTemplate());
    }

    // every few hours
    void updateNewsInterDay() {}

    void updateNewsDaily() {}

    public void getHourlyTrendingTopics(String accepterAuthKey) {
        client.retrieve(HttpRequest.GET("https://google-trends.p.rapidapi.com/api/v1/DailyTrendingSearches/Ref12345/US/15/20200923")
                .header("x-rapidapi-key", "25e6997e1emshad2ca9c5aa94e76p13fc8fjsn58f134a8dce5")
                .header("x-rapidapi-host", "google-trends.p.rapidapi.com"))
                .map((response) -> NewsArticle.builder().build());
    }
}