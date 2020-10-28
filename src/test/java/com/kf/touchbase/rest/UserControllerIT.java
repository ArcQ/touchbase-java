package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.UserMapper;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.UserReq;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.services.TouchbaseEventPublisher;
import com.kf.touchbase.testUtils.TestAuthUtils;
import com.kf.touchbase.testUtils.TestObjectFactory;
import com.kf.touchbase.testUtils.TestRestUtils;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.security.token.validator.TokenValidator;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static com.kf.touchbase.testUtils.TestAuthUtils.AUTHED_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


/**
 * Integration tests for the {@Link UserResource} REST controller.
 */
@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires(env = "test")
public class UserControllerIT {

    @Inject
    @Client("/")
    RxHttpClient client;
    @Inject
    private UserMapper userMapper;
    @Inject
    private UserRepository userRepository;
    @Inject
    private TouchbaseEventPublisher touchbaseEventPublisher;

    @Replaces(TokenAuthenticationFetcher.class)
    @Bean
    public AuthenticationFetcher stubAuthFetcher(
            TestAuthUtils.StubJwtTokenValidator stubJwtTokenValidator) {
        return new TestAuthUtils.StubAuthenticationFetcher(stubJwtTokenValidator);
    }

    @Bean
    public TokenValidator getJwtTokenValidator() {
        return new TestAuthUtils.StubAuthenticatedJwtTokenValidator();
    }

    @BeforeEach
    public void setup() {
        touchbaseEventPublisher = mock(TouchbaseEventPublisher.class);
    }

    @AfterEach
    public void cleanUpTest() {
        userRepository.deleteAll()
                .blockingAwait();
    }

    @Test
    public void getMe() {
        var user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user)
                .blockingGet();

        var result = client.retrieve(HttpRequest.GET("/api/v1/user/me")
                        .bearerAuth(AUTHED_USER),
                Argument.of(User.class))
                .blockingFirst();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void createUser() {
        var result = client.retrieve(HttpRequest.POST("/api/v1/user",
                TestObjectFactory.Dto.createUserReq())
                        .bearerAuth(AUTHED_USER),
                Argument.of(User.class))
                .blockingFirst();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userRepository.findByAuthKey(TestObjectFactory.AUTH_KEY)
                        .blockingGet());
    }

    @Test
    public void updateUser() {
        var user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user)
                .blockingGet();

        var result = client.retrieve(HttpRequest.PUT("/api/v1/user",
                TestObjectFactory.Dto.createUserReq())
                        .bearerAuth(AUTHED_USER),
                Argument.of(User.class))
                .blockingFirst();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userRepository.findByAuthKey(TestObjectFactory.AUTH_KEY)
                        .blockingGet());
    }

    @Test
    public void update_nonExistingUserShouldCreate() {
        int databaseSizeBeforeUpdate = userRepository.findAll()
                .toList()
                .blockingGet()
                .size();

        // Create the User
        UserReq userReq = TestObjectFactory.Dto.createUserReq();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        var result = client.retrieve(HttpRequest.POST("/api/v1/user",
                TestObjectFactory.Dto.createUserReq())
                        .bearerAuth(AUTHED_USER),
                Argument.of(User.class))
                .blockingFirst();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userRepository.findByAuthKey(TestObjectFactory.AUTH_KEY)
                        .blockingGet());

        // Validate the User in the database
        List<User> userList = userRepository.findAll()
                .toList()
                .blockingGet();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        TestRestUtils.equalsVerifier(User.class);
        User user1 = new User();
        user1.setId(uuid1);
        User user2 = new User();
        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);
        user2.setId(uuid2);
        assertThat(user1).isNotEqualTo(user2);
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }
}
