package com.kf.touchbase.rest;

import com.agorapulse.micronaut.aws.s3.SimpleStorageService;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.SignedUrlRes;
import com.kf.touchbase.testUtils.TestObjectFactory;
import com.kf.touchbase.testUtils.TestRestUtils;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Integration tests for the {@Link BaseResource} REST controller.
 */
public class UploadControllerTest {

    private UploadController uploadController;

    private SimpleStorageService simpleStorageService;

    private Authentication authentication = TestObjectFactory.authentication;

    @BeforeEach
    public void setup() {
        simpleStorageService = mock(SimpleStorageService.class);
        when(simpleStorageService.generatePresignedUrl(eq("testKey"), any(Date.class))).thenReturn(
                "http://s3testurl");
        uploadController = new UploadController(simpleStorageService);
    }

    @Test
    public void getSignedUrl() {
        SignedUrlRes signedUrlRes =
                uploadController.upload(authentication, Optional.of("testKey")).blockingGet();
        assertThat(signedUrlRes.getUrl()).isEqualTo("http://s3testurl");
    }

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
