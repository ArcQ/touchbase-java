package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.MemberReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMemberMapper {

    BaseMember toEntity(MemberReq memberReq);

    List<BaseMember> toEntities(List<MemberReq> memberReq);
}
