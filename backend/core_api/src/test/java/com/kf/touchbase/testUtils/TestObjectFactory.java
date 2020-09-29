package com.kf.touchbase.testUtils;

import com.kf.touchbase.models.domain.MissionTemplate;
import com.kf.touchbase.models.domain.MissionType;
import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.Mission;
import com.kf.touchbase.models.domain.postgres.StoredMissionTemplate;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.UserReq;
import com.nimbusds.jwt.JWTClaimsSet;
import org.assertj.core.api.Condition;

import java.util.LinkedHashMap;
import java.util.UUID;

public class TestObjectFactory {
    public static UUID USER_ID = UUID.randomUUID();
    public static String BASE_NAME = "Tony's Base 1";
    public static String IMAGE_URL = "imageUrl";
    public static String FIRST_NAME = "Tony";
    public static String LAST_NAME = "Stark";
    public static String USERNAME = "arcq";
    public static String EMAIL = "tony.stark@gmail.com";
    public static String AUTH_KEY = "710f7b05-8911-4285-98f9-2cc292352e36";

    public static JWTClaimsSet createJwtClaimsSet() {
        return new JWTClaimsSet.Builder()
                .subject(AUTH_KEY)
                .issuer("https://cognito-idp.us-east-1.amazonaws.com/us-east-1_HnccgMQBx")
                .jwtID("9118844c-7b91-4643-be65-11f0a0e4a2a0")
                .claim("token_use", "access")
                .claim("scope", "aws.cognito.signin.user.admin openid profile email")
                .claim("auth_time", 1591242586)
                .claim("exp", 1591246186)
                .claim("iat", 1591242586)
                .claim("version", 2)
                .claim("client_id", "2a672bhg11if5bo6fni8spctc4")
                .claim("username", USERNAME)
                .build();
    }

    public static Condition<LinkedHashMap> testObjectUser =
            new Condition<>((LinkedHashMap linkedHashMap) -> linkedHashMap.get("id") != null
                    && linkedHashMap.get("authKey").equals(AUTH_KEY)
                    && linkedHashMap.get("email").equals(EMAIL)
                    && linkedHashMap.get("firstName").equals(FIRST_NAME)
                    && linkedHashMap.get("lastName").equals(LAST_NAME)
                    && linkedHashMap.get("username").equals(USERNAME), "response matches " +
                    "testObject user");

    public static class Domain {
        public static Mission createMission() {
            return Mission.builder()
                    .assignedBase(createBase())
                    .assignedUser(createUser())
                    .build();
        }

        public static StoredMissionTemplate createOneTimeMissionTemplate() {
            return StoredMissionTemplate.builder()
                    .storedMissionTemplate(MissionTemplate
                            .builder()
                            .scoreReward(10.0)
                            .name("test mission one time")
                            .missonType(MissionType.ONE_TIME)
                            .description("test mission earn 10 points")
                            .build())
                    .build();
        }

        public static User createUser() {
            return User.builder()
                    .id(USER_ID)
                    .authKey(AUTH_KEY)
                    .email(EMAIL)
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .username(USERNAME)
                    .build();
        }

        public static Base createBase() {
            User user = createUser();
            Base base = Base.builder()
                    .id(UUID.randomUUID())
                    .name(BASE_NAME)
                    .score(0.0)
                    .imageUrl(IMAGE_URL)
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

        public static UserReq createUserReq() {
            var userReq = new UserReq();
            userReq.setAuthKey(AUTH_KEY);
            userReq.setEmail(EMAIL);
            userReq.setFirstName(FIRST_NAME);
            userReq.setLastName(LAST_NAME);
            userReq.setUsername(USERNAME);
            return userReq;
        }
    }
}
