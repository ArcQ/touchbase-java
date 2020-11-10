package com.kf.touchbase.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {AuthorizationException.class, ExceptionHandler.class})
public class AuthorizationExceptionHandler implements ExceptionHandler<AuthorizationException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, AuthorizationException exception) {
        return HttpResponse.status(HttpStatus.FORBIDDEN);
    }
}
