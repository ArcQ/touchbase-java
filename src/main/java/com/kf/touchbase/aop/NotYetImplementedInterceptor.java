package com.kf.touchbase.aop;

import com.kf.touchbase.exception.NotYetImplementedException;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;

import javax.inject.Singleton;

@Singleton
public class NotYetImplementedInterceptor implements MethodInterceptor<Object, Object> {
    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        throw new NotYetImplementedException();
    }
}
