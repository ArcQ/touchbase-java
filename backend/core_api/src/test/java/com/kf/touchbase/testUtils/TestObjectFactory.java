package com.kf.touchbase.testUtils;

import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.BaseReq;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.UUID;

public class TestObjectFactory {
    public static String AUTH_KEY = "AuthKey1";
    public static UUID USER_ID = UUID.randomUUID();

    public static JWTClaimsSet createJwtClaimsSet() {
        return new JWTClaimsSet.Builder()
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
    }

    public static class Domain {
        public static User createUser() {
            return User.builder()
                    .id(USER_ID)
                    .authKey(AUTH_KEY)
                    .email("tony.stark@gmail.com")
                    .firstName("Tony")
                    .lastName("Stark")
                    .username("arcq")
                    .authKey("710f7b05-8911-4285-98f9-2cc292352e36")
                    .build();
        }

        public static Base createBase() {
            User user = createUser();
            Base base = Base.builder()
                    .id(UUID.randomUUID())
                    .name("Tony's Base 1")
                    .score(0.0)
                    .imageUrl("imageUrl")
                    .creator(user)
                    .build();
            base.addMember(user, Role.ADMIN);
            return base;
        }
    }

    public static class Dto {
        public static BaseReq createBaseReq() {
            return new BaseReq("Tony's Base 1", "imageUrl");
        }
    }
}
