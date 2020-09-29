package com.kf.touchbase.rest;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.ListReq;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.testUtils.TestAuthUtils;
import com.kf.touchbase.testUtils.TestObjectFactory;
import com.kf.touchbase.testUtils.TestRestUtils;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.UUID;

import static com.kf.touchbase.testUtils.TestAuthUtils.AUTHED_USER;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration tests for the {@Link BaseResource} REST controller.
 */
@MicronautTest(transactional = false)
//@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires(env = "test")
public class BaseControllerIT {

    @Inject
    private BaseRepository baseRepository;
    @Inject
    private BaseMemberRepository baseMemberRepository;
    @Inject
    private UserRepository userRepository;

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
        userRepository.save(user);
    }

    @AfterEach
    public void cleanUpTest() {
        baseMemberRepository.deleteAll();
        baseRepository.deleteAll();
    }

    @Test
    public void createBaseAndThenGet() {
        int databaseSizeBeforeCreate = baseRepository.findAll().count().blockingGet().intValue();

        var baseReq = TestObjectFactory.Dto.createBaseReq();

        var response = client.exchange(HttpRequest.POST("/api/v1/base",
                baseReq).bearerAuth(AUTHED_USER), BaseReq.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        var result =
                client.retrieve(HttpRequest.GET("/api/v1/base").bearerAuth(AUTHED_USER),
                        Argument.of(ListReq.class, Base.class)).blockingFirst();

        var expectedResult = TestObjectFactory.Domain.createBase();

        assertThat(result.getList()).hasSize(databaseSizeBeforeCreate + 1);

        Base resultBase = (Base) result.getList().get(0);

        assertThat(resultBase).usingRecursiveComparison().ignoringFieldsMatchingRegexes(
                "(.*?)createdAt", "(.*?)updatedAt", "(.*?)id", "members").isEqualTo(expectedResult);

        var members = (HashSet) resultBase.getMembers();
        var member = (BaseMember) members.iterator().next();
        assertThat(member.getUser()).isEqualTo(member);
    }

    @Test
    public void testUnAuthorized() {
        var user = TestObjectFactory.Domain.createUser();
        user.setId(null);
        userRepository.save(user);

        var baseReq = TestObjectFactory.Dto.createBaseReq();

        var response = client.exchange(HttpRequest.POST("/api/v1/base",
                baseReq).bearerAuth("Not a user"), BaseReq.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.FORBIDDEN.getCode());
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
    public void equalsVerifier() throws Exception {
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
