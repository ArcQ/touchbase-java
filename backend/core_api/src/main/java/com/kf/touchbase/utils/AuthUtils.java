package com.kf.touchbase.utils;

import io.micronaut.security.authentication.Authentication;
import io.reactivex.Single;

public class AuthUtils {
    public static String getUsernameFromAuth(Authentication authentication) {
        return (String) authentication.getAttributes().get("username");
    }

    public static String getAuthKeyFromAuth(Authentication authentication) {
        return (String) authentication.getAttributes().get("sub");
    }

    public static Single<String> getAuthKeyFromAuthRx(Authentication authentication) {
        return  Single.just((String) authentication.getAttributes().get("sub"));
    }
}
