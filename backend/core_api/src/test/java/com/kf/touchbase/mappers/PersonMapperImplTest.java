package com.kf.touchbase.mappers;

import com.kf.touchbase.models.domain.Person;
import com.kf.touchbase.models.dto.PersonReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class PersonMapperImplTest {

    private PersonMapperImpl personMapperImplUnderTest;

    @BeforeEach
    void setUp() {
        personMapperImplUnderTest = new PersonMapperImpl();
    }

    @Test
    void testPersonReqToPerson() {
        // Setup
        final PersonReq person = new PersonReq("authId", "email", 0.0, "first_name", "last_name", "username");
        final Person expectedResult = Person.builder()
                .authId("authId")
                .email("email")
                .score(0.0)
                .firstName("first_name")
                .lastName("last_name")
                .username("username").build();

        // Run the test
        final Person result = personMapperImplUnderTest.personReqToPerson(person);

        // Verify the results
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }
}
