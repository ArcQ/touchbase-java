package com.kf.touchbase.models.domain.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticleMeta {

    String sourceId;

    String sourceName;

    String author;

    String title;

    String description;

    String url;

    String urlToImage;

    String publishedAt;

    String content;
}
