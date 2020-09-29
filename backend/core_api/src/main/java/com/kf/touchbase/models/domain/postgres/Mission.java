package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.MissionTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    private ZonedDateTime startedAt;

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @ManyToOne
    @JoinTable(name = "mission_assigned_user")
    private User assignedUser;

    @ManyToOne
    @JoinTable(name = "mission_assigned_base")
    private Base assignedBase;

    @Embedded
    private MissionTemplate storedMissionTemplate;
}
