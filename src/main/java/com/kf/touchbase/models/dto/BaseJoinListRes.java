package com.kf.touchbase.models.dto;

import com.kf.touchbase.models.domain.postgres.BaseJoin;
import lombok.*;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseJoinListRes {
    List<BaseJoin> requestsList;
    List<BaseJoin> invitesList;
}
