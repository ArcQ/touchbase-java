package com.kf.touchbase.models.domain.postgres;

import com.kf.touchbase.models.domain.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User extends TouchBasePostgresDomain implements Serializable {

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    protected LocalDateTime updatedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "auth_key")
    private String authKey;

    private String username;

    private String email;

    @Builder.Default
    private Double score = 0.0;

    private String firstName;

    private String lastName;

    private String imageUrl;

    @Transient
    private Role role;

    public User merge(User user) {
        if (user.getUsername() != null) {
            this.setUsername(email);
        }
        this.username = (user.getUsername() == null) ? this.username : user.getUsername();
        this.email = (user.getEmail() == null) ? this.email : user.getEmail();
        this.firstName = (user.getFirstName() == null) ? this.firstName : user.getFirstName();
        this.lastName = (user.getLastName() == null) ? this.lastName : user.getLastName();
        this.imageUrl = (user.getImageUrl() == null) ? this.imageUrl : user.getImageUrl();

        return this;
    }

    public static User fromId(UUID userId) {
        var user = new User();
        user.setId(userId);
        return user;
    }

    public static User fromAuthKey(String authKey) {
        var user = new User();
        user.setAuthKey(authKey);
        return user;
    }
}
