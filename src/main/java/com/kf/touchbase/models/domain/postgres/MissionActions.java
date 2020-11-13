package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.MissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "missionType")
public class MissionActions extends TouchBasePostgresDomain{

    @Enumerated(EnumType.STRING)
    MissionType missionType;

}
