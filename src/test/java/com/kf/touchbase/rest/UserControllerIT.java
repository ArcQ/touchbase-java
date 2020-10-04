package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.UserMapper;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.repository.UserRepository;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.util.UUID;

import static com.kf.touchbase.testUtils.TestAuthUtils.AUTHED_USER;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration tests for the {@Link UserResource} REST controller.
 */
@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires(env = "test")
public class UserControllerIT {

    @Inject
    private UserMapper userMapper;
    @Inject
    private UserRepository userRepository;

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

    @Inject
    @Client("/")
    RxHttpClient client;

    @AfterEach
    public void cleanUpTest() {
        userRepository.deleteAll().blockingAwait();
    }

    @Test
    public void getMe() {
        var user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user).blockingGet();

        var result = client.retrieve(HttpRequest.GET("/api/v1/user/me").bearerAuth(AUTHED_USER),
                Argument.of(User.class)).blockingFirst();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void createUser() {
        var result = client.retrieve(HttpRequest.POST("/api/v1/user",
                TestObjectFactory.Dto.createUserReq()).bearerAuth(AUTHED_USER),
                Argument.of(User.class)).blockingFirst();

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userRepository.findByAuthKey(TestObjectFactory.AUTH_KEY).blockingGet());
    }

    //    @Test
    //    public void updateUserThenGet() throws Exception {
    //        int databaseSizeBeforeCreate = userRepository.findAll().size();
    //
    //        UserReq userReq = TestObjectFactory.Dto.createUserReq();
    //
    //        HttpResponse<UserReq> response = client.exchange(HttpRequest.PATCH("/api/v1/users",
    //        userReq)
    //                , UserReq.class).blockingFirst();
    //
    //        assertThat(response.status().getCode()).isEqualTo(HttpStatus.ACCEPTED.getCode());
    //
    //        List<User> result = client.retrieve(HttpRequest.GET("/api/users?eagerload=true"),
    //                Argument.listOf(User.class)).blockingFirst();
    //
    //        assertThat(result).hasSize(databaseSizeBeforeCreate + 1);
    //
    //        User resultUser = result.get(0);
    //        assertThat(resultUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_DATE);
    //        assertThat(resultUser.getUpdatedAt()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    //        assertThat(resultUser.getName()).isEqualTo(DEFAULT_NAME);
    //        assertThat(resultUser.getScore()).isEqualTo(DEFAULT_SCORE);
    //        assertThat(resultUser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    //        assertThat(resultUser.isActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    //    }

    //    @Test
    //    public void updateNonExistingUser() throws Exception {
    //        int databaseSizeBeforeUpdate = userRepository.findAll().size();
    //
    //        // Create the User
    //        UserReq userReq = TestObjectFactory.Dto.createUserReq();
    //
    //        // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //        @SuppressWarnings("unchecked")
    //        HttpResponse<UserReq> response = client.exchange(HttpRequest.PUT("/api/users",
    //        userReq), UserReq.class)
    //                .onErrorReturn(t -> (HttpResponse<UserReq>) ((HttpClientResponseException)
    //                t).getResponse()).blockingFirst();
    //
    //        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());
    //
    //        // Validate the User in the database
    //        List<User> userList = userRepository.findAll();
    //        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
    //    }
    //
    //        @Test
    //        public void deleteUser() throws Exception {
    //            userRepository.saveAndFlush(user);
    //
    //            int databaseSizeBeforeDelete = userRepository.findAll().size();
    //
    //            // Delete the user
    //            @SuppressWarnings("unchecked")
    //            HttpResponse<UserReq> response = client.exchange(HttpRequest.DELETE
    //            ("/api/users/" + user.getId()), UserReq.class)
    //                    .onErrorReturn(t -> (HttpResponse<UserReq>) (
    //                    (HttpClientResponseException) t).getResponse()).blockingFirst();
    //
    //            assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode
    //            ());
    //
    //            // Validate the database is now empty
    //            List<User> userList = userRepository.findAll();
    //            assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    //        }

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
