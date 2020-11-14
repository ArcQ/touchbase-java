package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.postgres.NewsArticle;
import com.kf.touchbase.models.dto.ListRes;
import com.kf.touchbase.services.NewsServiceImpl;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/api/v1/news")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class NewsController {

    private final NewsServiceImpl newsService;

    @Get("")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<ListRes<NewsArticle>> getNews(
            Authentication authentication,
            @QueryValue("topic") String topic) {
        return newsService.getArticlesFromTopic(topic)
                .toList()
                .flatMap((articles) -> Single.just(new ListRes<>(articles)));
    }
}
