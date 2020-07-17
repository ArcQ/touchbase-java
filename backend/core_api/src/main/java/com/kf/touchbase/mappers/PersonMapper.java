package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.neo4j.Person;
import com.kf.touchbase.models.dto.PersonReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    Person personReqToPerson(PersonReq person);
}
