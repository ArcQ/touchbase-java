package com.kf.touchbase.services;

import com.kf.touchbase.exception.AuthorizationException;
import com.kf.touchbase.models.domain.postgres.BaseJoin;
import com.kf.touchbase.models.domain.postgres.BaseJoinAction;
import com.kf.touchbase.models.domain.postgres.BaseMember;
import com.kf.touchbase.repository.BaseJoinRepository;
import com.kf.touchbase.repository.BaseMemberRepository;
import com.kf.touchbase.repository.BaseRepository;
import com.kf.touchbase.repository.UserRepository;
import com.kf.touchbase.testUtils.TestObjectFactory;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.kf.touchbase.testUtils.TestObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
class BaseMemberServiceImplTest {

    @Mock
    private BaseJoinRepository mockBaseJoinRepository;
    @Mock
    private BaseRepository mockBaseRepository;
    @Mock
    private BaseMemberRepository mockBaseMemberRepository;
    @Mock
    private UserRepository mockUserRepository;

    private BaseMemberServiceImpl baseMemberServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        baseMemberServiceImplUnderTest =
                new BaseMemberServiceImpl(mockBaseJoinRepository, mockBaseRepository,
                        mockBaseMemberRepository, mockUserRepository);
    }

    @Test
    void testCreateBaseJoin_inviteNotAdminShouldReject() {
        // Setup

        when(mockBaseRepository.findIfMemberAdmin(
                BASE_UUID, AUTH_KEY_2)).thenReturn(
                Maybe.empty());

        when(mockUserRepository.findByAuthKey(any(String.class))).thenReturn(Maybe.empty());

        // Run the test
        assertThatThrownBy(() -> baseMemberServiceImplUnderTest.createBaseJoin(AUTH_KEY_2,
                BASE_UUID, AUTH_KEY,
                BaseJoinAction.Invite)
                .blockingGet()).isInstanceOf(AuthorizationException.class);

        verify(mockBaseMemberRepository, times(0)).save(any());
    }


    @Test
    void testAcceptBaseJoin_inviteAcceptedByMatchingUserShouldInsertAndDelete() {
        // Setup
        final Maybe<BaseJoin> baseJoinMaybe =
                Maybe.just(TestObjectFactory.Domain.createBaseInviteJoin());

        when(mockBaseJoinRepository.findById(BASE_INVITE_UUID)).thenReturn(baseJoinMaybe);

        when(mockBaseJoinRepository.delete(
                eq(TestObjectFactory.Domain.createBaseInviteJoin()))).thenReturn(Completable.amb(
                List.of()));

        doReturn(Single.just(TestObjectFactory.Domain.createBaseMember())).when(
                mockBaseMemberRepository)
                .save(any(BaseMember.class));

        // Run the test
        baseMemberServiceImplUnderTest.acceptBaseJoin(AUTH_KEY_2,
                BASE_INVITE_UUID)
                .blockingGet();

        verify(mockBaseMemberRepository, times(1)).save(argThat((baseMember) -> baseMember.getBase()
                .getId()
                .equals(BASE_UUID) && baseMember
                .getUser()
                .getId()
                .equals(USER_ID_2)));

        verify(mockBaseJoinRepository, times(1)).delete(argThat((baseJoin) -> baseJoin.getBase()
                .getId()
                .equals(BASE_UUID) && baseJoin
                .getJoiningUser()
                .getId()
                .equals(USER_ID_2)));
    }
}
