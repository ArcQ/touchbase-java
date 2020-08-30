package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.MemberRef;
import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.models.dto.BaseReq;
import com.kf.touchbase.models.dto.MemberReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMapper {

    Base baseReqToBase(BaseReq base);

    MemberRef memberReqToMemberRef(MemberReq memberReq);

    List<MemberRef> memberReqsToMemberRefs(List<MemberReq> memberReq);
}
