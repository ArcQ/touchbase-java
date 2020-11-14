package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.MissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Accessors(chain = true)
public class MissionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    private String name;

    private String description;

    private Double scoreReward;

    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private MissionTemplateDetail detail;
}
