package com.kf.touchbase.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TouchbaseBeanUtils {
//    private static Logger logger;

    public static List<Field> getAllFieldsTilParent(Class<?> curClass, Class<?> stopParentClass) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(List.of(curClass.getDeclaredFields()));

        Class<?> parentClass = curClass.getSuperclass();

        if (parentClass != null && parentClass != stopParentClass) {
            fields.addAll(getAllFieldsTilParent(parentClass, stopParentClass));
        }

        return fields;
    }

    public static <T> void mergeInNotNull(T origObj, T updateObj, Class<?> stopParentClass,
                                          String... includeFields) {
        var errors = new ArrayList<>();
        List<Field> fields = getAllFieldsTilParent(origObj.getClass(), stopParentClass);

        fields.stream()
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
