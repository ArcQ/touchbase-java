package com.kf.touchbase.testUtils;

import com.kf.touchbase.models.domain.MissionTemplate;
import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.postgres.*;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.UserReq;
import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.validator.AuthenticationJWTClaimsSetAdapter;
import org.assertj.core.api.Condition;

import java.util.LinkedHashMap;
import java.util.List;
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
    public static UUID BASE_UUID = UUID.randomUUID();
    public static UUID BASE_INVITE_UUID = UUID.randomUUID();
    public static UUID BASE_REQUEST_UUID = UUID.randomUUID();

    public static Authentication authentication =
            new AuthenticationJWTClaimsSetAdapter(createJwtClaimsSet());

    public static Condition<LinkedHashMap> testObjectUser =
            new Condition<>((LinkedHashMap linkedHashMap) -> linkedHashMap.get("id") != null
                    && linkedHashMap.get("authKey")
                    .equals(AUTH_KEY)
                    && linkedHashMap.get("email")
                    .equals(EMAIL)
                    && linkedHashMap.get("firstName")
                    .equals(FIRST_NAME)
                    && linkedHashMap.get("lastName")
                    .equals(LAST_NAME)
                    && linkedHashMap.get("username")
                    .equals(USERNAME), "response matches " +
                    "testObject user");

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
                            //                            .missonType(MissionType.ONE_TIME)
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
                    .imageUrl(IMAGE_URL)
                    .build();
        }

        public static Base createBase() {
            User user = createUser();
            Base base = Base.builder()
                    .id(BASE_UUID)
                    .name(BASE_NAME)
                    .score(0.0)
                    .imageUrl(IMAGE_URL)
                    .creator(user)
                    .build();
            base.addMember(user, Role.ADMIN);
            return base;
        }

        public static BaseJoin createBaseInviteJoin() {
            User user = createUser();
            Base base = createBase();
            return BaseJoin.builder()
                    .id(BASE_INVITE_UUID)
                    .base(base)
                    .joiningUser(user)
                    .baseJoinAction(BaseJoinAction.Invite)
                    .creator(user)
                    .build();
        }

        public static BaseJoin createBaseRequestJoin() {
            User user = createUser();
            Base base = createBase();
            return BaseJoin.builder()
                    .id(BASE_INVITE_UUID)
                    .base(base)
                    .joiningUser(user)
                    .baseJoinAction(BaseJoinAction.Invite)
                    .creator(user)
                    .build();
        }

        public static BaseMember createBaseMember() {
            var creator = createUser();
            return BaseMember.builder()
                    .base(createBase())
                    .user(creator)
                    .creator(creator)
                    .role(Role.ADMIN)
                    .build();
        }
    }

    public static class Dto {
        public static BaseReq createBaseReq() {
            return new BaseReq("Tony's Base 1", "imageUrl");
        }

        public static UserReq createUserReq() {
            var userReq = new UserReq();
            userReq.setEmail(EMAIL);
            userReq.setFirstName(FIRST_NAME);
            userReq.setLastName(LAST_NAME);
            userReq.setUsername(USERNAME);
            userReq.setImageUrl(IMAGE_URL);
            return userReq;
        }

        public static BaseJoinListRes createBaseJoinListRes() {
            var requestsList = List.of(Domain.createBaseInviteJoin());
            var invitesList = List.of(Domain.createBaseRequestJoin());
            return new BaseJoinListRes(requestsList, invitesList);
        }

        public static BaseJoinReq createBaseJoinListReq() {
            User user = Domain.createUser();
            Base base = Domain.createBase();
            base.addMember(user, Role.ADMIN);
            return new BaseJoinReq(BASE_UUID.toString(), AUTH_KEY, BaseJoinAction.Invite);
        }
    }
}
