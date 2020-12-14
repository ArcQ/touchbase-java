package com.kf.touchbase.models.domain.postgres;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kf.touchbase.models.domain.Role;
import io.micronaut.data.annotation.Where;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
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
@JsonIgnoreProperties(value = {"admins"})
public class Base extends TouchBasePostgresDomain {

    private String name;

    @Builder.Default()
    private Double score = 0.0;
    private String imageUrl;

    @Builder.Default()
    @JsonIgnore
    private boolean isActive = true;

    @OneToMany(mappedBy = "base", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"id", "base", "createdAt"})
    @Builder.Default
    private Set<BaseMember> members = new LinkedHashSet<>();

    @OneToMany(mappedBy = "base", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"id", "base"})
    @Builder.Default
    private Set<Chat> chats = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private BaseType type;

    public void mergeInNotNull(Base base) {
        name = (base.name == null) ? name : base.name;
        imageUrl = (base.imageUrl == null) ? imageUrl : base.imageUrl;
    }

    public void addMember(User user, Role role) {
        this.members.add(new BaseMember(this, user, role));
    }

    public void removeMember(UUID userUuid) {
        this.members.removeIf((baseMember) -> baseMember.getUser().getId().equals(userUuid));
    }

    public static Base fromId(UUID baseId) {
        var base = new Base();
        base.setId(baseId);
        return base;
    }
}
