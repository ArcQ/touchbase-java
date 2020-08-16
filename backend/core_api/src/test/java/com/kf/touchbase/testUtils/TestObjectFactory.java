package com.kf.touchbase.testUtils;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.BaseReq;

import java.util.Set;
import java.util.UUID;

public class TestObjectFactory {
    public static String authId = "authId1";
    public static UUID userId = UUID.randomUUID();

    public static class Domain {
        public static User createUser() {
            return User.builder()
                    .uuid(userId)
                    .authId(authId)
                    .email("tony.stark@gmail.com")
                    .firstName("Tony")
                    .lastName("Stark")
                    .build();
        }

        public static Base createBase() {
            User user = createUser();
            Base base = Base.builder()
                    .uuid(UUID.randomUUID())
                    .name("Tony's Base 1")
                    .score(0.0)
                    .imageUrl("imageUrl")
                    .admins(Set.of(user))
                    .build();
            return base;
        }
    }

    public static class Dto {
        public static BaseReq createBaseReq() {
            return new BaseReq("Tony's Base 1", "imageUrl");
        }
    }
}
