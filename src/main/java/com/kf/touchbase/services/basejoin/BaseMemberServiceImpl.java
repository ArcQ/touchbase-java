package com.kf.touchbase.services.basejoin;

import com.kf.touchbase.repository.BaseJoinRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@RequiredArgsConstructor
@Singleton
public class BaseMemberServiceImpl {

    private final BaseJoinRepository baseJoinRepository;
}
