package com.example.ca4u_backend.common.utils;

import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CollectionsUtil {

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }
}
