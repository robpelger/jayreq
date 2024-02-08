package io.badgod.jayreq;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Headers extends LinkedHashMap<String, List<String>> {

    public boolean isPresent() {
        return size() > 0;
    }

    public static Headers of(String key, String... values) {
        if (isNullOrEmpty(key)) {
            return new Headers();
        } else if (isNullOrEmpty(values)) {
            throw new IllegalArgumentException("Empty header values are not allowed!");
        } else {
            Headers headers = new Headers();
            headers.put(key, List.of(values));
            return headers;
        }
    }

    private static boolean isNullOrEmpty(String key) {
        return key == null || key.isBlank();
    }

    private static boolean isNullOrEmpty(String... values) {
        return values == null || values.length == 0;
    }

    public String[] toStringArray() {
        var arr = new String[entrySet().size() * 2];
        AtomicInteger i = new AtomicInteger(0);
        Map<String, String> flat = entrySet().stream()
            .collect(Collectors.toMap(entry -> entry.getKey(), entry -> toStr(entry.getValue())));

        flat.entrySet().forEach(entry -> {
            arr[i.getAndIncrement()] = entry.getKey();
            arr[i.getAndIncrement()] = entry.getValue();
        });
        return arr;
    }

    private String toStr(List<String> value) {
        return value.stream().collect(Collectors.joining(","));
    }
}
