package com.kf.touchbase.services;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.testUtils.TestObjectFactory;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

class OwnedRepositoryServiceTest {

    private OwnedRepositoryService<Base> ownedRepositoryServiceUnderTest;

    private final Base base = TestObjectFactory.Domain.createBase();

    @Mock
    JpaRepository<Base,UUID> repository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        ownedRepositoryServiceUnderTest = new OwnedRepositoryService<>() {
            @NonNull
            @Override
            public <S extends Base> List<S> saveAll(@NonNull @Valid @NotNull Iterable<S> entities) {
                return null;
            }

            @Override
            public List<Base> findAll() {
                return null;
            }

            @NonNull
            @Override
            public List<Base> findAll(@NonNull Sort sort) {
                return null;
            }

            @NonNull
            @Override
            public Page<Base> findAll(@NonNull Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Base> S saveAndFlush(@NonNull @Valid @NotNull S entity) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public <S extends Base> S update(@Valid @NotNull S entity) {
                return null;
            }

            @Override
            public Optional<Base> findById(UUID id) {
                return repository.findById(id);
            }

            @Override
            public boolean existsById(@NotNull UUID uuid) {
                return false;
            }

            @Override
            public void deleteById(UUID id) {

            }

            @Override
            public void delete(@NotNull Base entity) {

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
    void testFindIfAdmin() {
        // Setup
        Mockito.when(repository.findById(base.getId())).thenReturn(Optional.of(base));

        // Run the test
        Base result =
                ownedRepositoryServiceUnderTest.findIfAdmin(base.getAdmins().iterator().next().getAuthKey(),
                base);

        // Verify the results
        assertThat(result).isEqualTo(base);
    }

    @Test
    void testFindIfAdminId() {
        // Setup
        Mockito.when(repository.findById(base.getId())).thenReturn(Optional.of(base));

        // Run the test
        Base result =
                ownedRepositoryServiceUnderTest.findIfAdminId(base.getAdmins().iterator().next().getAuthKey(),
                base.getId(), Base.class);

        // Verify the results
        assertThat(result).isEqualTo(base);
    }

    @Test
    void testFindIfAdmin_noUserThrowsIllegalArgumentException() {
        Mockito.when(repository.findById(base.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            ownedRepositoryServiceUnderTest.findIfAdmin("incorrectId", base);
        });
    }

    @Test
    void testFindIfAdmin_ThrowsSecurityException() {
        Mockito.when(repository.findById(base.getId())).thenReturn(Optional.ofNullable(base));
        assertThrows(SecurityException.class, () -> {
            ownedRepositoryServiceUnderTest.findIfAdmin("user2", base);
        });
    }
}
