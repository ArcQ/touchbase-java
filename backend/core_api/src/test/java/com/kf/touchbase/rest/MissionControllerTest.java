package com.kf.touchmission.rest;

import com.kf.touchbase.repository.MissionRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.rest.MissionController;
import com.kf.touchbase.testUtils.TestAuthUtils;
import com.kf.touchbase.testUtils.TestObjectFactory;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.security.token.validator.TokenValidator;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;

@MicronautTest(transactional = false)
//@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires(env = "test")
class MissionControllerTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private MissionRepository missionRepository;

    private MissionController missionControllerUnderTest;

    @Replaces(TokenAuthenticationFetcher.class)
    @Bean
    public AuthenticationFetcher stubAuthFetcher(TestAuthUtils.StubJwtTokenValidator stubJwtTokenValidator) {
        return new TestAuthUtils.StubAuthenticationFetcher(stubJwtTokenValidator);
    }

    @Bean
    public TokenValidator getJwtTokenValidator() {
        return new TestAuthUtils.StubAuthenticatedJwtTokenValidator();
    }

    @Inject
    @Client("/")
    RxHttpClient client;

    @BeforeAll
    public void setup() {
        var user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user).blockingGet();
    }

    @AfterEach
    public void cleanUpTest() {
        missionRepository.deleteAll().blockingAwait();
        userRepository.deleteAll().blockingAwait();
    }

//    @Test
//    public void getMyMissions() {
//        int databaseSizeBeforeCreate = missionRepository.findAll().count().blockingGet().intValue();
//
//        var response = client.exchange(HttpRequest.GET("/api/v1/mission").bearerAuth(AUTHED_USER)).blockingFirst();
//
//        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());
//        var result = response.getBody(Argument.of(ListReq.class, Mission.class));
//
//        assertThat(result.get().getList().size()).isEqualTo(2);
//    }
//
//    @Test
//    void testProgressMission() {
//        // Setup
//        final ProgressReq progressReq = new ProgressReq(0);
//        final Mission expectedResult = new Mission();
//
//        // Run the test
//        final Mission result = missionControllerUnderTest.progressMission(progressReq);
//
//        // Verify the results
//        assertThat(expectedResult).isEqualTo(result);
//    }
}
