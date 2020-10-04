package com.kf.touchbase.models.domain.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Chat extends TouchBasePostgresDomain {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    Base base;

    @Column(name = "chat_id", insertable = false, updatable = false)
    String chatpiChatId;
}
