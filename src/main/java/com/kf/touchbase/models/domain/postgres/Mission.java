package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Mission {
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    private LocalDateTime startedAt;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_auth_key", referencedColumnName = "auth_key")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "base")
    private Base assignedBase;

    @ManyToOne
    private MissionTemplate missionTemplate;

    @OneToMany
    @JoinColumn(name = "mission_id", referencedColumnName = "id")
    private List<MissionAction> missionActions;
}
