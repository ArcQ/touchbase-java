package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.UserReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class UserMapperImplTest {

    private UserMapperImpl userMapperImplUnderTest;

    @BeforeEach
    void setUp() {
        userMapperImplUnderTest = new UserMapperImpl();
    }

    @Test
    void testUserReqToUser() {
        // Setup
        final UserReq user = new UserReq("authId", "email", 0.0, "first_name", "last_name", "username");
        final User expectedResult = User.builder()
                .authId("authId")
                .email("email")
                .score(0.0)
                .firstName("first_name")
                .lastName("last_name")
                .username("username").build();

        // Run the test
        final User result = userMapperImplUnderTest.userReqToUser(user);

        // Verify the results
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }
}
