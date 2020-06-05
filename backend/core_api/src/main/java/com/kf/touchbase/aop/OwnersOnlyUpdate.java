package com.kf.touchbase.aop;

import io.micronaut.context.annotation.Type;
import io.micronaut.aop.Around;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Around
@Type(OwnersOnlyUpdateInterceptor.class)
public @interface OwnersOnlyUpdate {
}
