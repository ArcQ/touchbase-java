package com.kf.touchbase.services.postgres.repository;

import com.kf.touchbase.models.domain.postgres.Base;
import com.kf.touchbase.services.OwnedRepositoryService;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseRepository extends OwnedRepositoryService<Base> {

    @Query(value = "SELECT b.* FROM base b, base_member m, tb_user u WHERE m.base_uuid=b.uuid AND m.member_uuid=u.uuid AND u.auth_id=:userAuthId", nativeQuery = true)
    List<Base> findAllByMember(String userAuthId);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.uuid='?1' AND m.base_uuid=b.uuid AND m.member_uuid=u.uuid AND u.auth_id='?2'", nativeQuery = true)
    Optional<Base> findIfMember(UUID baseId, String userAuthId);

    @Query(value = "SELECT b.* FROM base b, base_member m, user u WHERE b.uuid = ?1 AND m.base_uuid=b.uuid AND m.member_uuid=u.uuid AND u.auth_id='?2' AND m.role='member'", nativeQuery = true)
    Optional<Base> findIfMemberAdmin(UUID baseId, String userAuthId);
}
