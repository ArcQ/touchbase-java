package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.UserReq;
import com.kf.touchbase.testUtils.TestObjectFactory;
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
        final UserReq user = TestObjectFactory.Dto.createUserReq();
        final User expectedResult = TestObjectFactory.Domain.createUser();
        expectedResult.setId(null);
        expectedResult.setAuthKey(null);
        expectedResult.setScore(null);

        // Run the test
        final User result = userMapperImplUnderTest.userReqToUser(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
