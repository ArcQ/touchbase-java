package com.kf.touchbase.models.domain.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BaseJoin extends TouchBasePostgresDomain {
    @ManyToOne
    @JoinColumn(name = "base_id", updatable = false)
    Base base;

    @ManyToOne
    @JoinColumn(name = "joining_user_auth_key", referencedColumnName = "auth_key")
    User joiningUser;

    @Enumerated(EnumType.STRING)
    BaseJoinAction baseJoinAction;
}
