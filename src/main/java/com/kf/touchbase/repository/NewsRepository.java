package com.kf.touchbase.repository;

import com.kf.touchbase.models.domain.postgres.NewsArticle;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends RxJavaCrudRepository<NewsArticle, UUID> {

    List<NewsArticle> findAllByTopic(String topic);
}
