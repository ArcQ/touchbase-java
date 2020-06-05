package com.kf.touchbase.utils;

import io.micronaut.security.authentication.Authentication;

public class AuthUtils {
    public static String getUsernameFromAuth(Authentication authentication) {
        return (String) authentication.getAttributes().get("username");
    }
}
