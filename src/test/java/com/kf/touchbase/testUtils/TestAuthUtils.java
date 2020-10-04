package com.kf.touchbase.testUtils;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.token.jwt.validator.AuthenticationJWTClaimsSetAdapter;
import io.micronaut.security.token.validator.TokenValidator;
import io.reactivex.Flowable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;

public class TestAuthUtils {

    public static String AUTHED_USER = "authedUser";

    private TestAuthUtils() {
    }

    public AuthenticationFetcher stubAuthFetcher(StubJwtTokenValidator stubJwtTokenValidator) {
        return new StubAuthenticationFetcher(stubJwtTokenValidator);
    }

    @AllArgsConstructor
    public static class StubAuthenticationFetcher implements AuthenticationFetcher {

        StubJwtTokenValidator stubJwtTokenValidator;

        @Override
        public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
            if (request.getHeaders().getAuthorization().isPresent()) {
                return stubJwtTokenValidator.validateToken("");
            }
            return Flowable.empty();
        }
    }

    @Singleton
    public static class StubAuthenticatedJwtTokenValidator extends StubJwtTokenValidator {

        public StubAuthenticatedJwtTokenValidator() {
            super();
            var jwtClaimsSet = TestObjectFactory.createJwtClaimsSet();

            Authentication authentication = new AuthenticationJWTClaimsSetAdapter(jwtClaimsSet);
            super.authentication = authentication;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class StubJwtTokenValidator implements TokenValidator {

        Authentication authentication;

        @Override
        public Publisher<Authentication> validateToken(String token) {
            return Flowable.just(authentication);
        }

    }
}
