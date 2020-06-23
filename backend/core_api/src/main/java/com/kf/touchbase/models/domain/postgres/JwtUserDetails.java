package com.kf.touchbase.models.domain.postgres;

import io.micronaut.security.authentication.UserDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@ToString
@EqualsAndHashCode(callSuper = true)
public class JwtUserDetails extends UserDetails {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String clientId;

    public JwtUserDetails(String username, Collection<String> roles) {
        super(username, roles);
    }

    public JwtUserDetails(String username, Collection<String> roles, String email, String client_id) {
        super(username, roles);
        this.email = email;
        this.clientId = client_id;
    }
}
