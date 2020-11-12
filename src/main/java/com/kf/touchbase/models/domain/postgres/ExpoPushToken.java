package com.kf.touchbase.models.domain.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity()
@Table(name = "expo_push_token")
@SuperBuilder()
@AllArgsConstructor
@NoArgsConstructor
public class ExpoPushToken extends TouchBasePostgresDomain {
    @ManyToOne()
    @JoinColumn(name = "user_auth_key", referencedColumnName = "auth_key")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
