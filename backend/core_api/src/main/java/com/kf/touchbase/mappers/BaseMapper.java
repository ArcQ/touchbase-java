package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.dto.BaseReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseMapper {

    Base baseReqToBase(BaseReq base);
}
