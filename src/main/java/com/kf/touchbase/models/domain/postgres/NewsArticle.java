package com.kf.touchbase.models.domain.postgres;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class NewsArticle extends TouchBasePostgresDomain {
    String topic;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    NewsArticleDetail data;

    LocalDateTime expiresAt;
}
