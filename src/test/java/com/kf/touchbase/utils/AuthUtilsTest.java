package com.kf.touchbase.utils;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.validator.AuthenticationJWTClaimsSetAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class AuthUtilsTest {

    Authentication authentication;

    @BeforeEach
    void setup() {
        var jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject("710f7b05-8911-4285-98f9-2cc292352e36")
                .issuer("https://cognito-idp.us-east-1.amazonaws.com/us-east-1_HnccgMQBx")
                .jwtID("9118844c-7b91-4643-be65-11f0a0e4a2a0")
                .claim("token_use", "access")
                .claim("scope", "aws.cognito.signin.user.admin openid profile email")
                .claim("auth_time", 1591242586)
                .claim("exp", 1591246186)
                .claim("iat", 1591242586)
                .claim("version", 2)
                .claim("client_id", "2a672bhg11if5bo6fni8spctc4")
                .claim("username", "arcq")
                .build();

        authentication = new AuthenticationJWTClaimsSetAdapter(jwtClaimsSet);
    }

    @Test
    void testGetUsernameFromAuth() {
        // Run the test
        final var result = AuthUtils.getUsernameFromAuth(authentication);

        // Verify the results
        assertThat(result).isEqualTo("arcq");
        assertThat(result).isEqualTo("arcq");
    }

    @Test
    void testGetUserIdFromAuth() {
        // Run the test
        final var result = AuthUtils.getAuthKeyFromAuth(authentication);

        // Verify the results
        assertThat(result).isEqualTo("710f7b05-8911-4285-98f9-2cc292352e36");
    }
}

