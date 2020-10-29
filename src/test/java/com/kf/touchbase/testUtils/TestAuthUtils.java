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

    public static String AUTH_TOKEN = "authedUser";
    public static String AUTH_TOKEN_2 = "authedUser2";

    private TestAuthUtils() {
    }

    @AllArgsConstructor
    public static class StubAuthenticationFetcher implements AuthenticationFetcher {

        StubJwtTokenValidator stubJwtTokenValidator;

        @Override
        public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
            return stubJwtTokenValidator.validateToken(request.getHeaders()
                    .getAuthorization()
                    .orElse("").replaceAll("Bearer ", ""));
        }
    }

    @NoArgsConstructor
    @Singleton
    public static class StubJwtTokenValidator implements TokenValidator {

        @Override
        public Publisher<Authentication> validateToken(String token) {
            if (token.equals(AUTH_TOKEN)) {
                var jwtClaimsSet = TestObjectFactory.createJwtClaimsSet();
                Authentication authentication = new AuthenticationJWTClaimsSetAdapter(jwtClaimsSet);
                return Flowable.just(authentication);
            } else if (token.equals(AUTH_TOKEN_2)) {
                var jwtClaimsSet2 = TestObjectFactory.createJwtClaimsSet2();
                Authentication authentication2 =
                        new AuthenticationJWTClaimsSetAdapter(jwtClaimsSet2);
                return Flowable.just(authentication2);
            } else {
                return Flowable.empty();
            }
        }

    }
}
