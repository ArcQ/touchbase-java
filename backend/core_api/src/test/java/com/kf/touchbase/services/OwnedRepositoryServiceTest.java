package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.neo4j.Base;
import com.kf.touchbase.testUtils.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;

class OwnedRepositoryServiceTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory mockSessionFactory;

    private OwnedRepositoryService<Base> ownedRepositoryServiceUnderTest;

    private final Base base = TestObjectFactory.createBase();

    @BeforeEach
    void setUp() {
        initMocks(this);
        ownedRepositoryServiceUnderTest = new OwnedRepositoryService<>() {
            @Override
            public Iterable<Base> findAll() {
                return null;
            }

            @Override
            public Optional<Base> findById(UUID id) {
                return Optional.ofNullable(session.load(Base.class, id, 1));
            }

            @Override
            public void deleteById(UUID id) {

            }

            @Override
            public Base save(Base object) {
                return null;
            }

            @Override
            public Iterable<Base> findByOwnerId(String ownerAuthId, Class<Base> clazz) {
                return null;
            }
        };
        Mockito.when(mockSessionFactory.openSession()).thenReturn(session);
    }

    @Test
    void testFindIfOwner() {
        // Setup
        Mockito.when(session.load(Base.class, base.getUuid(), 1)).thenReturn(base);

        // Run the test
        Base result = ownedRepositoryServiceUnderTest.findIfOwner(base.getOwner().getAuthId(),
                base);

        // Verify the results
        assertThat(result).isEqualTo(base);
    }

    @Test
    void testFindIfOwnerId() {
        // Setup
        Mockito.when(session.load(Base.class, base.getUuid(), 1)).thenReturn(base);

        // Run the test
        Base result = ownedRepositoryServiceUnderTest.findIfOwnerId(base.getOwner().getAuthId(),
                base.getUuid(), Base.class);

        // Verify the results
        assertThat(result).isEqualTo(base);
    }

    @Test
    void testFindIfOwner_noUserThrowsIllegalArgumentException() {
        Mockito.when(session.load(eq(Base.class), any(), eq(1))).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            ownedRepositoryServiceUnderTest.findIfOwner("incorrectId", base);
        });
    }

    @Test
    void testFindIfOwner_ThrowsSecurityException() {
        Mockito.when(session.load(Base.class, base.getUuid(), 1)).thenReturn(base);
        assertThrows(SecurityException.class, () -> {
            ownedRepositoryServiceUnderTest.findIfOwner("person2", base);
        });
    }
}
