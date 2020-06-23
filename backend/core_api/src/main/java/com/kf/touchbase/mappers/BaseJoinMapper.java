package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.dto.BaseJoinReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseJoinMapper {

    BaseJoin basejoinReqToInvite(BaseJoinReq baseJoinReq);

    BaseJoin basejoinReqToRequest(BaseJoinReq baseJoinReq);
}
