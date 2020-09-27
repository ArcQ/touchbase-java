package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="base_member")
@EqualsAndHashCode(callSuper = true)
public class BaseMember extends TouchBasePostgresDomain {

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "base_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Base base;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;
}
