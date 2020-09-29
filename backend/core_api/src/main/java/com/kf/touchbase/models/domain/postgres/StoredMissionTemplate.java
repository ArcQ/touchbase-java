package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.MissionTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "mission_template")
public class StoredMissionTemplate extends TouchBasePostgresDomain {
    @Embedded
    private MissionTemplate storedMissionTemplate;
}
