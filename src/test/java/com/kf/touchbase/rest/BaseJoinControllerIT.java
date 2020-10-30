package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.Role;
import com.kf.touchbase.models.domain.postgres.*;
import com.kf.touchbase.models.dto.BaseJoinListRes;
import com.kf.touchbase.models.dto.BaseJoinReq;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.services.BaseMemberServiceImpl;
import com.kf.touchbase.services.EventPublisher;
import com.kf.touchbase.testUtils.TestAuthUtils;
import com.kf.touchbase.testUtils.TestBeanUtils;
import com.kf.touchbase.testUtils.TestObjectFactory;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.security.token.validator.TokenValidator;
import io.micronaut.test.annotation.MicronautTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static com.kf.touchbase.testUtils.TestAuthUtils.AUTH_TOKEN;
import static com.kf.touchbase.testUtils.TestAuthUtils.AUTH_TOKEN_2;
import static com.kf.touchbase.testUtils.TestObjectFactory.AUTH_KEY_2;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(transactional = false)
//@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires(env = "test")
@Slf4j
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BaseJoinControllerIT {

    @Inject
    @Client("/")
    RxHttpClient client;
    @Inject
    private BaseRepository baseRepository;
    @Inject
    private BaseMemberRepository baseMemberRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private BaseMemberServiceImpl baseMemberService;
    @Inject
    private BaseJoinController baseJoinControllerUnderTest;

    private User admin;

    private User newMember;

    private Base base;

    private BaseMember baseMember;

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
    void setUp() {
        admin = TestObjectFactory.Domain.createUser();
        newMember = TestObjectFactory.Domain.createNewUser();
        admin.setId(null);
        newMember.setId(null);
        base = TestObjectFactory.Domain.createBase();
        base.setId(null);
        base.setCreator(null);
        baseMember = TestObjectFactory.Domain.createBaseMember();
        userRepository.saveAll(List.of(newMember, admin))
                .blockingSubscribe();
        baseRepository.save(base)
                .blockingGet();
    }

    @Test
    void testInviteNewMember() {
        // invite a new member
        var baseInviteReq = new BaseJoinReq(base.getId(), AUTH_KEY_2, BaseJoinAction.INVITE);

        var response = client.exchange(HttpRequest.POST("/api/v1/baseJoin",
                baseInviteReq)
                .bearerAuth(AUTH_TOKEN), BaseReq.class)
                .blockingFirst();

        assertThat(response.status()
                .getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // get baseJoins
        var result =
                client.retrieve(HttpRequest.GET("/api/v1/baseJoin")
                                .bearerAuth(AUTH_TOKEN_2),
                        Argument.of(BaseJoinListRes.class))
                        .blockingFirst();

        assertThat(result.getInvitesList()).hasSize(1);
        assertThat(result.getRequestsList()).hasSize(0);

        var expectedResult = new BaseJoin(base, newMember, BaseJoinAction.INVITE);
        var invite = result.getInvitesList()
                .get(0);
        assertThat(invite).usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(
                        "(.*?)createdAt", "(.*?)updatedAt", "(.*?)id", "(.*?)members")
                .isEqualTo(expectedResult);

        assertThat(invite
                .getBase()
                .getMembers()).hasSize(1);

        //        var bodyOfMessage = messages.poll(2, TimeUnit.SECONDS);
        //        assertThat(bodyOfMessage.getKey()).isEqualTo("upsert-chat-entity");
        //        var event = (ChatEntityEventData) bodyOfMessage.getEvent()
        //                .getData();

        // then new member accept
        var acceptedResponse =
                client.exchange(HttpRequest.POST("/api/v1/baseJoin/" + invite.getId(), new Object())
                        .bearerAuth(AUTH_TOKEN_2), BaseMember.class)
                        .blockingFirst();

        assertThat(acceptedResponse.status()
                .getCode()).isEqualTo(HttpStatus.CREATED.getCode());
        var baseMember = acceptedResponse.getBody()
                .get();
        assertThat(baseMember.getUser()
                .getAuthKey()).isEqualTo(newMember.getAuthKey());
        assertThat(baseMember.getBase()
                .getId()).isEqualTo(invite.getBase()
                .getId());
        assertThat(baseMember.getRole()).isEqualTo(Role.MEMBER);
    }

        @Test
        void testRequestBaseJoin() {
            // as a new member request into group
            var baseInviteReq = new BaseJoinReq(base.getId(), AUTH_KEY_2, BaseJoinAction.REQUEST);

            var response = client.exchange(HttpRequest.POST("/api/v1/baseJoin",
                    baseInviteReq)
                    .bearerAuth(AUTH_TOKEN), BaseReq.class)
                    .blockingFirst();

            assertThat(response.status()
                    .getCode()).isEqualTo(HttpStatus.CREATED.getCode());

            // get baseJoin requests
            var result =
                    client.retrieve(HttpRequest.GET("/api/v1/baseJoin")
                                    .bearerAuth(AUTH_TOKEN),
                            Argument.of(BaseJoinListRes.class))
                            .blockingFirst();

            assertThat(result.getInvitesList()).hasSize(0);
            assertThat(result.getRequestsList()).hasSize(1);

            var expectedResult = new BaseJoin(base, newMember, BaseJoinAction.INVITE);
            var invite = result.getRequestsList()
                    .get(0);
            assertThat(invite).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(
                            "(.*?)createdAt", "(.*?)updatedAt", "(.*?)id", "(.*?)members")
                    .isEqualTo(expectedResult);

            assertThat(invite
                    .getBase()
                    .getMembers()).hasSize(1);

            //        var bodyOfMessage = messages.poll(2, TimeUnit.SECONDS);
            //        assertThat(bodyOfMessage.getKey()).isEqualTo("upsert-chat-entity");
            //        var event = (ChatEntityEventData) bodyOfMessage.getEvent()
            //                .getData();

            // then admin accepts
            var acceptedResponse =
                    client.exchange(HttpRequest.POST("/api/v1/baseJoin/" + invite.getId(), new Object())
                            .bearerAuth(AUTH_TOKEN), BaseMember.class)
                            .blockingFirst();

            assertThat(acceptedResponse.status()
                    .getCode()).isEqualTo(HttpStatus.CREATED.getCode());
            var baseMember = acceptedResponse.getBody()
                    .get();
            assertThat(baseMember.getUser()
                    .getAuthKey()).isEqualTo(newMember.getAuthKey());
            assertThat(baseMember.getBase()
                    .getId()).isEqualTo(invite.getBase()
                    .getId());
            assertThat(baseMember.getRole()).isEqualTo(Role.MEMBER);

            // after, should have no more base joins
            var resultAfter =
                    client.retrieve(HttpRequest.GET("/api/v1/baseJoin")
                                    .bearerAuth(AUTH_TOKEN),
                            Argument.of(BaseJoinListRes.class))
                            .blockingFirst();

            assertThat(resultAfter.getInvitesList()).hasSize(0);
            assertThat(resultAfter.getRequestsList()).hasSize(0);

            //base member created
            assertThat(baseMemberRepository.findByUserAuthKeyAndBaseId(AUTH_TOKEN_2,
                    base.getId()).isEmpty().blockingGet()).isFalse();
        }

        @Test
        void testDeclineBaseJoin() {
            // as a new member request into group
            var baseInviteReq = new BaseJoinReq(base.getId(), AUTH_KEY_2, BaseJoinAction.REQUEST);

            var response = client.exchange(HttpRequest.POST("/api/v1/baseJoin",
                    baseInviteReq)
                    .bearerAuth(AUTH_TOKEN), BaseReq.class)
                    .blockingFirst();

            assertThat(response.status()
                    .getCode()).isEqualTo(HttpStatus.CREATED.getCode());

            // get baseJoin requests
            var result =
                    client.retrieve(HttpRequest.GET("/api/v1/baseJoin")
                                    .bearerAuth(AUTH_TOKEN),
                            Argument.of(BaseJoinListRes.class))
                            .blockingFirst();

            assertThat(result.getInvitesList()).hasSize(1);
            assertThat(result.getRequestsList()).hasSize(0);

            var expectedResult = new BaseJoin(base, newMember, BaseJoinAction.INVITE);
            var invite = result.getInvitesList()
                    .get(0);
            assertThat(invite).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(
                            "(.*?)createdAt", "(.*?)updatedAt", "(.*?)id", "(.*?)members")
                    .isEqualTo(expectedResult);

            assertThat(invite
                    .getBase()
                    .getMembers()).hasSize(1);

            //        var bodyOfMessage = messages.poll(2, TimeUnit.SECONDS);
            //        assertThat(bodyOfMessage.getKey()).isEqualTo("upsert-chat-entity");
            //        var event = (ChatEntityEventData) bodyOfMessage.getEvent()
            //                .getData();

            // then admin accepts
            var acceptedResponse =
                    client.exchange(HttpRequest.DELETE("/api/v1/baseJoin/" + invite.getId(),
                            new Object())
                            .bearerAuth(AUTH_TOKEN_2), BaseMember.class)
                            .blockingFirst();

            assertThat(acceptedResponse.status()
                    .getCode()).isEqualTo(HttpStatus.OK.getCode());

            // after, should have no more basejoins
            var resultAfter =
                    client.retrieve(HttpRequest.GET("/api/v1/baseJoin")
                                    .bearerAuth(AUTH_TOKEN),
                            Argument.of(BaseJoinListRes.class))
                            .blockingFirst();

            assertThat(resultAfter.getInvitesList()).hasSize(0);
            assertThat(resultAfter.getRequestsList()).hasSize(0);

            // base member created
            assertThat(baseMemberRepository.findByUserAuthKeyAndBaseId(AUTH_TOKEN_2,
                    base.getId()).isEmpty().blockingGet()).isFalse();
        }
}
