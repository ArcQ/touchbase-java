package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.postgres.Person;
import com.kf.touchbase.models.dto.PersonPublicRes;
import com.kf.touchbase.models.dto.PersonReq;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    Person personReqToPerson(PersonReq person);

    PersonPublicRes personToPersonPublicRes(Person person);

    Iterable<PersonPublicRes> personIterableToPersonPublicResIterable(Iterable<Person> person);
}
