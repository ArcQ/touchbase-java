package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.event.ChatEntityEventData;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.ListReq;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.services.EventPublisher;
import com.kf.touchbase.testUtils.TestAuthUtils;
import com.kf.touchbase.testUtils.TestBeanUtils;
import com.kf.touchbase.testUtils.TestObjectFactory;
import com.kf.touchbase.testUtils.TestRestUtils;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.security.token.validator.TokenValidator;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.kf.touchbase.testUtils.TestAuthUtils.AUTH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


/**
 * Integration tests for the {@Link BaseResource} REST controller.
 */
@MicronautTest(transactional = false)
//@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires(env = "test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BaseControllerIT {

    @Inject
    @Client("/")
    RxHttpClient client;
    @Inject
    private BaseRepository baseRepository;
    @Inject
    private ApplicationContext applicationContext;
    @Inject
    private BaseMemberRepository baseMemberRepository;
    @Inject
    private UserRepository userRepository;
    private User user;

    private BlockingQueue<TestBeanUtils.EventWithKey> messages = new LinkedBlockingDeque<>();

    @Replaces(EventPublisher.class)
    @Bean
    public TestBeanUtils.StubTouchbaseEventPublisher stubEventsPublisher() {
        return new TestBeanUtils.StubTouchbaseEventPublisher(messages);
    }

    @Replaces(TokenAuthenticationFetcher.class)
    @Bean
    public AuthenticationFetcher stubAuthFetcher(
            TestAuthUtils.StubJwtTokenValidator stubJwtTokenValidator) {
        return new TestAuthUtils.StubAuthenticationFetcher(stubJwtTokenValidator);
    }

    @Bean
    public TokenValidator getJwtTokenValidator() {
        return new TestAuthUtils.StubJwtTokenValidator();
    }

    @BeforeAll
    public void setup() {
        user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user).blockingGet();
    }

    @AfterEach
    public void cleanUpTest() {
        baseMemberRepository.deleteAll().blockingAwait();
        baseRepository.deleteAll().blockingAwait();
    }

    @Test
    public void create_base_and_then_get_should_be_correct() throws InterruptedException {
        int databaseSizeBeforeCreate = baseRepository.findAll().count().blockingGet().intValue();

        var baseReq = TestObjectFactory.Dto.createBaseReq();

        var response = client.exchange(HttpRequest.POST("/api/v1/base",
                baseReq).bearerAuth(AUTH_TOKEN), BaseReq.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        var result =
                client.retrieve(HttpRequest.GET("/api/v1/base").bearerAuth(AUTH_TOKEN),
                        Argument.of(ListReq.class, Base.class)).blockingFirst();

        var expectedResult = TestObjectFactory.Domain.createBase();

        var bodyOfMessage = messages.poll(2, TimeUnit.SECONDS);

        assertThat(bodyOfMessage.getKey()).isEqualTo("upsert-chat-entity");

        var event = (ChatEntityEventData) bodyOfMessage.getEvent().getData();

        assertThat(event.getEntity()).usingRecursiveComparison().ignoringFieldsMatchingRegexes(
                "(.*?)createdAt", "(.*?)updatedAt", "(.*?)id", "chats", "members").isEqualTo(expectedResult);

        assertThat(result.getList()).hasSize(databaseSizeBeforeCreate + 1);

        Base resultBase = (Base) result.getList().get(0);

        assertThat(resultBase).usingRecursiveComparison().ignoringFieldsMatchingRegexes(
                "(.*?)createdAt", "(.*?)updatedAt", "(.*?)id", "members").isEqualTo(expectedResult);

        var members = (HashSet) resultBase.getMembers();
        var member = (BaseMember) members.iterator().next();
        assertThat(member.getUser()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void create_base_and_then_get_should_fail() {
        var user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user);

        var baseReq = TestObjectFactory.Dto.createBaseReq();

        assertThatThrownBy(() ->
                client.exchange(HttpRequest.POST("/api/v1/base?",
                        baseReq), BaseReq.class).blockingFirst()).isInstanceOf(
                HttpClientResponseException.class).hasMessage("Unauthorized");
    }

    //    @Test
    //    public void updateBaseThenGet() throws Exception {
    //        int databaseSizeBeforeCreate = baseRepository.findAll().size();
    //
    //        BaseReq baseReq = TestObjectFactory.Dto.createBaseReq();
    //
    //        HttpResponse<BaseReq> response = client.exchange(HttpRequest.PATCH("/api/v1/bases",
    //        baseReq)
    //                , BaseReq.class).blockingFirst();
    //
    //        assertThat(response.status().getCode()).isEqualTo(HttpStatus.ACCEPTED.getCode());
    //
    //        List<Base> result = client.retrieve(HttpRequest.GET("/api/bases?eagerload=true"),
    //                Argument.listOf(Base.class)).blockingFirst();
    //
    //        assertThat(result).hasSize(databaseSizeBeforeCreate + 1);
    //
    //        Base resultBase = result.get(0);
    //        assertThat(resultBase.getCreatedAt()).isEqualTo(DEFAULT_CREATED_DATE);
    //        assertThat(resultBase.getUpdatedAt()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    //        assertThat(resultBase.getName()).isEqualTo(DEFAULT_NAME);
    //        assertThat(resultBase.getScore()).isEqualTo(DEFAULT_SCORE);
    //        assertThat(resultBase.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    //        assertThat(resultBase.isActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    //    }

    //    @Test
    //    public void updateNonExistingBase() throws Exception {
    //        int databaseSizeBeforeUpdate = baseRepository.findAll().size();
    //
    //        // Create the Base
    //        BaseReq baseReq = TestObjectFactory.Dto.createBaseReq();
    //
    //        // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //        @SuppressWarnings("unchecked")
    //        HttpResponse<BaseReq> response = client.exchange(HttpRequest.PUT("/api/bases",
    //        baseReq), BaseReq.class)
    //                .onErrorReturn(t -> (HttpResponse<BaseReq>) ((HttpClientResponseException)
    //                t).getResponse()).blockingFirst();
    //
    //        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());
    //
    //        // Validate the Base in the database
    //        List<Base> baseList = baseRepository.findAll();
    //        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);
    //    }
    //
    //    @Test
    //    public void deleteBase() throws Exception {
    //        // Initialize the database with one entity
    //        baseRepository.saveAndFlush(base);
    //
    //        int databaseSizeBeforeDelete = baseRepository.findAll().size();
    //
    //        // Delete the base
    //        @SuppressWarnings("unchecked")
    //        HttpResponse<BaseReq> response = client.exchange(HttpRequest.DELETE("/api/bases/" +
    //        base.getId()), BaseReq.class)
    //                .onErrorReturn(t -> (HttpResponse<BaseReq>) ((HttpClientResponseException)
    //                t).getResponse()).blockingFirst();
    //
    //        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());
    //
    //        // Validate the database is now empty
    //        List<Base> baseList = baseRepository.findAll();
    //        assertThat(baseList).hasSize(databaseSizeBeforeDelete - 1);
    //    }

    @Test
    public void equals_verifier_should_match_all_conditions() throws Exception {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        TestRestUtils.equalsVerifier(Base.class);
        Base base1 = new Base();
        base1.setId(uuid1);
        Base base2 = new Base();
        base2.setId(base1.getId());
        assertThat(base1).isEqualTo(base2);
        base2.setId(uuid2);
        assertThat(base1).isNotEqualTo(base2);
        base1.setId(null);
        assertThat(base1).isNotEqualTo(base2);
    }
}
