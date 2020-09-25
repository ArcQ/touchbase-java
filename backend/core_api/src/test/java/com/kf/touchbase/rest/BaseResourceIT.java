package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.BaseMapper;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.services.postgres.repository.BaseRepository;
import com.kf.touchbase.testUtils.TestObjectFactory;
import com.kf.touchbase.testUtils.TestRestUtils;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration tests for the {@Link BaseResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;
    private static final Double SMALLER_SCORE = 1D - 1D;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private BaseMapper baseMapper;
    @Inject
    private BaseRepository baseRepository;

    @Inject
    @Client("/")
    RxHttpClient client;

    @AfterEach
    public void cleanUpTest() {
        baseRepository.deleteAll();
    }

    @Test
    public void createBaseAndThenGet() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();

        BaseReq baseReq = TestObjectFactory.Dto.createBaseReq();

        HttpResponse<BaseReq> response = client.exchange(HttpRequest.POST("/api/v1/bases", baseReq)
                , BaseReq.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        List<Base> result = client.retrieve(HttpRequest.GET("/api/bases?eagerload=true"),
                Argument.listOf(Base.class)).blockingFirst();

        assertThat(result).hasSize(databaseSizeBeforeCreate + 1);

        Base resultBase = result.get(0);
        assertThat(resultBase.getCreatedAt()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(resultBase.getUpdatedAt()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(resultBase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(resultBase.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(resultBase.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(resultBase.isActive()).isEqualTo(DEFAULT_IS_ACTIVE);

    }

//    @Test
//    public void updateBaseThenGet() throws Exception {
//        int databaseSizeBeforeCreate = baseRepository.findAll().size();
//
//        BaseReq baseReq = TestObjectFactory.Dto.createBaseReq();
//
//        HttpResponse<BaseReq> response = client.exchange(HttpRequest.PATCH("/api/v1/bases", baseReq)
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
//        HttpResponse<BaseReq> response = client.exchange(HttpRequest.PUT("/api/bases", baseReq), BaseReq.class)
//                .onErrorReturn(t -> (HttpResponse<BaseReq>) ((HttpClientResponseException) t).getResponse()).blockingFirst();
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
//        HttpResponse<BaseReq> response = client.exchange(HttpRequest.DELETE("/api/bases/" + base.getId()), BaseReq.class)
//                .onErrorReturn(t -> (HttpResponse<BaseReq>) ((HttpClientResponseException) t).getResponse()).blockingFirst();
//
//        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());
//
//        // Validate the database is now empty
//        List<Base> baseList = baseRepository.findAll();
//        assertThat(baseList).hasSize(databaseSizeBeforeDelete - 1);
//    }

    @Test
    public void equalsVerifier() throws Exception {
        TestRestUtils.equalsVerifier(Base.class);
    }
}
