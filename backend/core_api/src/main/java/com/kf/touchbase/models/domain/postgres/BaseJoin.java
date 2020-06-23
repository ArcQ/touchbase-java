package com.kf.touchbase.models.domain.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BaseJoin extends TouchBasePostgresEntity {
    String baseId;
    BaseJoinAction baseJoinAction;
}
