package com.kf.touchbase.models.domain.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode
@Entity()
@Table(name = "expo_push_token")
@SuperBuilder()
@AllArgsConstructor
@NoArgsConstructor
public class ExpoPushToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "user_auth_key", referencedColumnName = "auth_key")
    private User user;

    private String token;

    private String deviceId;

    private Boolean isValid;

    private LocalDateTime expiresAt;
}
