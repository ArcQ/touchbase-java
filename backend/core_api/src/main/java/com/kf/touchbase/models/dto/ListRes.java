package com.kf.touchbase.models.dto;

import io.reactivex.Single;
import lombok.*;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ListRes<T> {
    List<T> list;

    public static <T> Single<ListRes<T>> toSingle(List<T> list) {
        return Single.just(new ListRes<T>(list));
    }
}
