package com.kf.touchbase.annotation;

import com.kf.touchbase.aop.NotYetImplementedInterceptor;
import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;
import io.micronaut.core.annotation.Internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TODO extend this with api doc and restrict on controller level, attaching to http annotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Around
@Type(NotYetImplementedInterceptor.class)
@Internal
public @interface NotYetImplemented {
}
