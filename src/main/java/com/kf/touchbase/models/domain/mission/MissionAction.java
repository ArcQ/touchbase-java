package com.kf.touchbase.models.domain.mission;

import com.kf.touchbase.models.domain.MissionType;
import com.kf.touchbase.models.domain.postgres.TouchBasePostgresDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "missionType")
@Accessors(chain = true)
public class MissionAction extends TouchBasePostgresDomain {

    @Enumerated(EnumType.STRING)
    MissionType missionType;
}
