package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObject(Object target, String fieldName, Object inject) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            boolean changedToAccessible = false;
            if (!field.canAccess(target)) {
                field.setAccessible(true);
                changedToAccessible = true;
                field.set(target, inject);
            }
            if (changedToAccessible) {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
