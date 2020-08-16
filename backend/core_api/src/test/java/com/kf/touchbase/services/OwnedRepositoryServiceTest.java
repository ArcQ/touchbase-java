package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.testUtils.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.data.repository.CrudRepository;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

class OwnedRepositoryServiceTest {

    private OwnedRepositoryService<Base> ownedRepositoryServiceUnderTest;

    private final Base base = TestObjectFactory.Domain.createBase();

    @Mock
    CrudRepository<Base,UUID> repository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        ownedRepositoryServiceUnderTest = new OwnedRepositoryService<>() {
            @Override
            public Iterable<Base> findAll() {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @NonNull
            @Override
            public <S extends Base> S update(@NonNull @Valid @NotNull S entity) {
                return null;
            }

            @NonNull
            @Override
            public <S extends Base> Iterable<S> saveAll(@NonNull @Valid @NotNull Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<Base> findById(UUID id) {
                return repository.findById(id);
            }

            @Override
            public boolean existsById(@NonNull @NotNull UUID uuid) {
                return false;
            }

            @Override
            public void deleteById(UUID id) {

            }

            @Override
            public void delete(@NonNull @NotNull Base entity) {

            }

            @Override
            public void deleteAll(@NonNull @NotNull Iterable<? extends Base> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public Base save(Base object) {
                return null;
            }

        };
    }

    @Test
    void testFindIfOwner() {
        // Setup
        Mockito.when(repository.findById(base.getUuid())).thenReturn(Optional.of(base));

        // Run the test
        Base result =
                ownedRepositoryServiceUnderTest.findIfOwner(base.getOwners().iterator().next().getAuthId(),
                base);

        // Verify the results
        assertThat(result).isEqualTo(base);
    }

    @Test
    void testFindIfOwnerId() {
        // Setup
        Mockito.when(repository.findById(base.getUuid())).thenReturn(Optional.of(base));

        // Run the test
        Base result =
                ownedRepositoryServiceUnderTest.findIfOwnerId(base.getOwners().iterator().next().getAuthId(),
                base.getUuid(), Base.class);

        // Verify the results
        assertThat(result).isEqualTo(base);
    }

    @Test
    void testFindIfOwner_noUserThrowsIllegalArgumentException() {
        Mockito.when(repository.findById(base.getUuid())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            ownedRepositoryServiceUnderTest.findIfOwner("incorrectId", base);
        });
    }

    @Test
    void testFindIfOwner_ThrowsSecurityException() {
        Mockito.when(repository.findById(base.getUuid())).thenReturn(Optional.ofNullable(base));
        assertThrows(SecurityException.class, () -> {
            ownedRepositoryServiceUnderTest.findIfOwner("person2", base);
        });
    }
}
