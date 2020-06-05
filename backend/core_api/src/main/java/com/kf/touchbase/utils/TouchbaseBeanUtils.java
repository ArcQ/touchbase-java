package com.kf.touchbase.utils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TouchbaseBeanUtils {
//    private static Logger logger;

    public static <T> void updateDomain(T origObj, T updateObj, String ...includeFields) {
        var errors = new ArrayList<>();
        Stream.concat(Arrays.stream(origObj.getClass().getDeclaredFields()),
                Arrays.stream(origObj.getClass().getSuperclass().getDeclaredFields())
        )
                .filter((field) -> Arrays.asList(includeFields).contains(field.getName()))
                .forEach((field) -> {
                    if (Modifier.isPrivate(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    try {
                        if (field.get(updateObj) != null) {
                            field.set(origObj, field.get(updateObj));
                        }
                    } catch (IllegalAccessException e) {
                        //TODO real error trace + error handle here
                        errors.add(e);
                    }
                });
        if (errors.size() > 0) {
            throw new IllegalArgumentException(String.format(
                    "Illegal field supplied to update method with errors: %s",
                    errors.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",", "[", "]"))));
        }
    }
}
