package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.Base;
import com.kf.touchbase.models.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

class AbstractEntityDataServiceTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory mockSessionFactory;

    private AbstractEntityDataService abstractEntityDataServiceUnderTest;
    private UUID uuid;
    private Base entity;

    @BeforeEach
    void setUp() {
        initMocks(this);
        abstractEntityDataServiceUnderTest = new AbstractEntityDataService(mockSessionFactory) {
            @Override
            Class<Base> getEntityType() {
                return Base.class;
            }
        };
        uuid = UUID.randomUUID();
        entity = Base.builder()
                .uuid(uuid)
                .name("name")
                .score(0.0)
                .imageUrl("imageUrl")
                .owner(Person.builder().username("person1").build())
                .build();
        Mockito.when(mockSessionFactory.openSession()).thenReturn(session);
    }

    @Test
    void testFindIfOwner() {
        // Setup
        Mockito.when(session.load(Base.class, uuid, 1)).thenReturn(entity);

        // Run the test
        Base result = (Base) abstractEntityDataServiceUnderTest.findIfOwner("person1", entity);

        // Verify the results
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void testFindIfOwnerId() {
        // Setup
        Mockito.when(session.load(Base.class, uuid, 1)).thenReturn(entity);

        // Run the test
        Base result = (Base) abstractEntityDataServiceUnderTest.findIfOwnerId("person1", entity.getUuid(), Base.class);

        // Verify the results
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void testFindIfOwner_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            abstractEntityDataServiceUnderTest.findIfOwner("person2", entity);
        });
    }

    @Test
    void testFindIfOwner_ThrowsSecurityException() {
        Mockito.when(session.load(Base.class, uuid, 1)).thenReturn(entity);
        assertThrows(SecurityException.class, () -> {
            abstractEntityDataServiceUnderTest.findIfOwner("person2", entity);
        });
    }
}
