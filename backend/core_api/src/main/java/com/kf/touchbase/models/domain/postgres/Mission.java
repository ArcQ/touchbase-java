package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class Mission extends TouchBasePostgresDomain {
    private ZonedDateTime startedAt;

    @ManyToOne
    @JoinTable(name = "mission_assigned_user")
    private User assignedUser;

    @ManyToOne
    @JoinTable(name = "mission_base_base")
    private Base assignedBase;
}
