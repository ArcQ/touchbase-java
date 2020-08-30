package com.kf.touchbase.models.domain.postgres;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kf.touchbase.models.domain.Role;
import io.micronaut.data.annotation.Where;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where("isActive = true")
@JsonIgnoreProperties(value = { "admins" })
public class Base extends TouchBasePostgresEntity {

    private String name;

    @Builder.Default()
    private Double score = 0.0;
    private String imageUrl;

    @Builder.Default()
    private boolean isActive = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"bases", "created", "owns"})
    @OneToMany(mappedBy = "base")
    private Set<BaseMember> members;

    public void mergeInNotNull(Base base) {
        name = (base.name == null) ? name : base.name;
        imageUrl = (base.imageUrl == null) ? imageUrl : base.imageUrl;
    }

    public void addMember(User user, Role role) {
        this.members.add(new BaseMember(this, user, role));
    }

    public void removeMember(UUID userUuid) {
        this.members.removeIf((baseMember) -> baseMember.getUser().getUuid().equals(userUuid));
    }
}
