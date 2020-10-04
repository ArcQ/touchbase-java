package com.kf.touchbase.models.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ListReq<T> {
    List<T> list;
}
