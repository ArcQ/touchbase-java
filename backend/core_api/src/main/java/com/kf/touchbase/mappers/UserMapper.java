package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.User;
import com.kf.touchbase.models.dto.UserPublicRes;
import com.kf.touchbase.models.dto.UserReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userReqToUser(UserReq user);

    UserPublicRes userToUserPublicRes(User user);

    Iterable<UserPublicRes> userIterableToUserPublicResIterable(Iterable<User> user);
}
