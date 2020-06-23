package com.kf.touchbase.utils;

import com.kf.touchbase.models.domain.neo4j.Person;
import com.kf.touchbase.models.domain.neo4j.TouchBaseNeo4jDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TouchbaseBeanUtilsTest {

    private Person person;

    @BeforeEach
    void setup() {
        person = Person.builder()
                .authId("authId")
                .email("email")
                .score(0.0)
                .firstName("first_name")
                .lastName("last_name")
                .username("username").build();
    }

    @Test
    public void updateDomain_nullLastName() {
        // Run the test
        final Person newPerson = Person.builder()
                .score(1.0)
                .firstName("eddie")
                .username("username").build();

        final Person result = Person.builder()
                .authId("authId")
                .email("email")
                .lastName("last_name")
                .score(1.0)
                .firstName("eddie")
                .username("username").build();

        // Run the test
        TouchbaseBeanUtils.mergeInNotNull(person, newPerson, TouchBaseNeo4jDomain.class, "email", "lastName", "score");

        // Verify the results
        assertThat(result).isEqualToComparingFieldByField(result);

    }

    @Test
    public void updateDomain_onlyemail() {
        // Run the test
        final Person newPerson = Person.builder()
                .email("eddie@gmail.com").build();

        final Person result = Person.builder()
                .authId("authId")
                .email("eddie@gmail.com")
                .lastName("last_name")
                .score(0.0)
                .firstName("first_name")
                .username("username").build();

        // Run the test
        TouchbaseBeanUtils.mergeInNotNull(person, newPerson, TouchBaseNeo4jDomain.class, "email", "lastName", "score");

        // Verify the results
        assertThat(result).isEqualToComparingFieldByField(person);

    }

    @Test
    public void updateDomain_noFieldsSpecified() {
        // Run the test
        final Person newPerson = Person.builder()
                .score(1.0)
                .firstName("eddie")
                .username("username").build();

        final Person result = Person.builder()
                .authId("authId")
                .email("email")
                .lastName("last_name")
                .score(0.0)
                .firstName("first_name")
                .username("username").build();

        // Run the test
        TouchbaseBeanUtils.mergeInNotNull(person, newPerson, TouchBaseNeo4jDomain.class);

        // Verify the results
        assertThat(result).isEqualToComparingFieldByField(person);

    }
}
