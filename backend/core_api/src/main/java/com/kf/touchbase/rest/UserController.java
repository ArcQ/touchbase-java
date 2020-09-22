package com.kf.touchbase.rest;

import com.kf.touchbase.mappers.UserMapper;
import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.UserPublicRes;
import com.kf.touchbase.models.dto.UserReq;
import com.kf.touchbase.services.postgres.UserService;
import com.kf.touchbase.utils.AuthUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Controller("/api/v1/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Get("/{?query}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Search for users to add")
    @ExecuteOn(TaskExecutors.IO)
    public Iterable<UserPublicRes> findAllPublic(Authentication authentication, Optional<String> query) {
        Iterable<User> userIterable = query.isPresent()
                ? userService.searchByQuery(query.get())
                : userService.findAll();

        return userMapper.userIterableToUserPublicResIterable(userIterable);
    }

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public User getMe(Authentication authentication) {
        return userService.findByAuthKey(AuthUtils.getAuthKeyFromAuth(authentication));
    }

    @Get("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public User getUser(String username) {
        return userService.findByUsername(username);
    }

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.MESSAGE_CONSUMER)
    public User postUser(Authentication authentication, @Body UserReq userReq) {
        var user = userMapper.userReqToUser(userReq);
        return userService.create(user);
    }
}
