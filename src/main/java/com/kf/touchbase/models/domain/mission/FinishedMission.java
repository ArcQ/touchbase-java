package com.kf.touchbase.models.domain.mission;

import com.kf.touchbase.models.domain.postgres.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
//@Entity
public class FinishedMission extends Mission {
    LocalDateTime finishedAt;
    boolean isCompleted;

    @ManyToOne(fetch = FetchType.EAGER)
    User user;
}
