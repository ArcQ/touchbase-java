package com.kf.touchbase.mappers;

import com.kf.touchbase.domain.Person;
import com.kf.touchbase.dto.PersonReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    Person personReqToPerson(PersonReq person);
}
