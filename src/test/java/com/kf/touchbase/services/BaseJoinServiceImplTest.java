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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kf.touchbase.testUtils.TestObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseJoinServiceImplTest {

    @Mock
    private BaseJoinRepository mockBaseJoinRepository;
    @Mock
    private BaseRepository mockBaseRepository;
    @Mock
    private BaseMemberRepository mockBaseMemberRepository;
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BaseJoinServiceImpl baseJoinServiceImplUnderTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateBaseJoin_inviteNotAdminShouldReject() {
        // Setup
        when(mockBaseRepository.findIfMemberAdmin(
                BASE_UUID, AUTH_KEY_2)).thenReturn(
                Maybe.empty());

        when(mockUserRepository.findByAuthKey(any(String.class))).thenReturn(Maybe.empty());

        // Run the test
        assertThatThrownBy(() -> baseJoinServiceImplUnderTest.createBaseJoin(AUTH_KEY_2,
                BASE_UUID, AUTH_KEY,
                BaseJoinAction.INVITE)
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
        baseJoinServiceImplUnderTest.acceptBaseJoin(AUTH_KEY_2,
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

    @Test
    void testAcceptBaseJoin_requestByNotAdminShouldRejectWithoutSavingMember() {
        // Setup
        final Maybe<BaseJoin> baseJoinMaybe =
                Maybe.just(TestObjectFactory.Domain.createBaseRequestJoin());

        when(mockBaseJoinRepository.findById(BASE_INVITE_UUID)).thenReturn(baseJoinMaybe);

        when(mockBaseRepository.findIfMemberAdmin(
                BASE_UUID, AUTH_KEY)).thenReturn(
                Maybe.empty());

        // Run the test

        assertThatThrownBy(() -> baseJoinServiceImplUnderTest.acceptBaseJoin(AUTH_KEY,
                        BASE_INVITE_UUID)
                        .blockingGet()).isInstanceOf(AuthorizationException.class);

        verify(mockBaseMemberRepository, times(0)).save(any());

        verify(mockBaseJoinRepository, times(0)).delete(any());
    }

    @Test
    void testAcceptBaseJoin_validRequestByAdminShouldSaveMember(){
        // Setup
        final Maybe<BaseJoin> baseJoinMaybe =
                Maybe.just(TestObjectFactory.Domain.createBaseRequestJoin());

        when(mockBaseJoinRepository.findById(BASE_REQUEST_UUID)).thenReturn(baseJoinMaybe);

        when(mockBaseRepository.findIfMemberAdmin(
                BASE_UUID, AUTH_KEY_2)).thenReturn(
                Maybe.just(TestObjectFactory.Domain.createBase()));

        when(mockBaseJoinRepository.delete(
                argThat((baseJoin) -> baseJoin.getBase()
                .getId()
                .equals(BASE_UUID) && baseJoin
                .getJoiningUser()
                .getId()
                .equals(USER_ID_2)))).thenReturn(Completable.amb(
                List.of()));

        doReturn(Single.just(TestObjectFactory.Domain.createBaseMember())).when(
                mockBaseMemberRepository)
                .save(any(BaseMember.class));

        // Run the test
        baseJoinServiceImplUnderTest.acceptBaseJoin(AUTH_KEY_2,
                BASE_REQUEST_UUID)
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
