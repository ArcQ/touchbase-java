package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@Entity(name="base_member")
public class BaseMember {
    @EmbeddedId
	private BaseMemberId id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "base_uuid", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Base base;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "member_uuid", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;

    public BaseMember(Base base, User user, Role role) {
        this.id = new BaseMemberId(base.getId(), user.getId());
        this.base = base;
        this.user = user;
        this.role = role;
    }

    @CreationTimestamp
    ZonedDateTime createdAt;

    @UpdateTimestamp
    ZonedDateTime updatedAt;

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class BaseMemberId implements Serializable {

        @Column(name = "base_uuid")
        protected UUID baseUuid;

        @Column(name = "member_uuid")
        protected UUID memberUuid;
    }
}
