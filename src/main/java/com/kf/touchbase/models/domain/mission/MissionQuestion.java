package com.kf.touchbase.models.domain.mission;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class MissionQuestion {
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Mission mission;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;

    private Boolean in_progress;

    private Integer order;
}
