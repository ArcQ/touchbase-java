package com.kf.touchbase.testUtils;

import com.nimbusds.jwt.JWTClaimsSet;
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

    private TestAuthUtils() {}

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
    public static class StubAuthenticatedJwtTokenValidator extends StubJwtTokenValidator{

        public StubAuthenticatedJwtTokenValidator() {
            super();
            var jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject("710f7b05-8911-4285-98f9-2cc292352e36")
                    .issuer("https://cognito-idp.us-east-1.amazonaws.com/us-east-1_HnccgMQBx")
                    .jwtID("9118844c-7b91-4643-be65-11f0a0e4a2a0")
                    .claim("token_use", "access")
                    .claim("scope", "aws.cognito.signin.user.admin openid profile email")
                    .claim("auth_time", 1591242586)
                    .claim("exp", 1591246186)
                    .claim("iat", 1591242586)
                    .claim("version", 2)
                    .claim("client_id", "2a672bhg11if5bo6fni8spctc4")
                    .claim("username", "arcq")
                    .build();

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
        };
    }
}
