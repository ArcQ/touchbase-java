package com.kf.touchbase.utils;

import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.security.authentication.Authentication;

import java.util.Collection;

/**
 * Utility class for Micronaut Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the authKey of the current user.
     *
     * @return the authKey of the current user.
     */
    public static String getCurrentAuthKey() {
        return (String) ServerRequestContext.currentRequest()
                .flatMap(request -> request.getUserPrincipal(Authentication.class))
                .map(Authentication::getAttributes).orElseThrow().get("sub");
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        return ServerRequestContext.currentRequest()
                .flatMap(request -> request.getUserPrincipal(Authentication.class))
                .isPresent();
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    @SuppressWarnings("unchecked")
    public static boolean isCurrentUserInRole(String authority) {
        return ServerRequestContext.currentRequest()
                .flatMap(request -> request.getUserPrincipal(Authentication.class))
                .map(authentication -> authentication.getAttributes().get("roles"))
                .filter(Collection.class::isInstance)
                .map(Collection.class::cast)
                .map(roles -> roles.stream().anyMatch(role -> role.equals(authority)))
                .orElse(false);
    }
}
