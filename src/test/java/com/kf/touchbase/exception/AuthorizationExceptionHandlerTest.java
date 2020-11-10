package com.kf.touchbase.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthorizationExceptionHandlerTest {

    private AuthorizationExceptionHandler authorizationExceptionHandlerUnderTest;

    @BeforeEach
    void setUp() {
        authorizationExceptionHandlerUnderTest = new AuthorizationExceptionHandler();
    }

    @Test
    void testHandle() {
        // Setup
        final HttpRequest request = null;
        final AuthorizationException exception = new AuthorizationException("message");

        // Run the test
        final HttpResponse result =
                authorizationExceptionHandlerUnderTest.handle(request, exception);

        // Verify the results
        assertThat(result.getStatus().getCode()).isEqualTo(403);
    }
}
