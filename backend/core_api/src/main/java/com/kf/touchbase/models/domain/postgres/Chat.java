package com.kf.touchbase.models.domain.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
public class Chat {
    @EmbeddedId
    private ChatId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_uuid", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    Base base;

    @Column(name="chat_id", insertable = false, updatable = false)
    String chatpiChatId;

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class ChatId implements Serializable {

        @Column(name = "base_uuid")
        protected UUID baseUuid;

        @Column(name = "chat_id")
        protected String chatpiChatId;
    }
}
