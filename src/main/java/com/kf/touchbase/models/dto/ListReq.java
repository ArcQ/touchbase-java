package com.kf.touchbase.models.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ListReq<T> {
    List<T> list;
}
