package com.kf.touchbase.testUtils;

import com.kf.touchbase.models.domain.neo4j.Base;
import com.kf.touchbase.models.domain.neo4j.Person;

import java.util.UUID;

public class TestObjectFactory {
    public static String authId = "authId1";
    public static Base createBase() {
        return Base.builder()
                .uuid(UUID.randomUUID())
                .name("name")
                .score(0.0)
                .imageUrl("imageUrl")
                .owner(Person.builder().authId(authId).build())
                .build();
    }
}
