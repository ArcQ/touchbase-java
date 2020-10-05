package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.MissionTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode()
@NoArgsConstructor
@Entity(name = "mission_template")
public class StoredMissionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Embedded
    private MissionTemplate storedMissionTemplate;
}
