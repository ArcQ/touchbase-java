package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.models.dto.MemberReq;

import java.util.List;

public interface BaseMemberMapper {

    BaseMember toEntity(MemberReq memberReq);

    List<BaseMember> toEntities(List<MemberReq> memberReq);
}
