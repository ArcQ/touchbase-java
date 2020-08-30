package com.kf.touchbase.models.dto;

import lombok.*;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ListRes<T> {
    List<T> list;
}
