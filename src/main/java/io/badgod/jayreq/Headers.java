package io.badgod.jayreq;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Headers {

    private final Map<String, List<String>> headersMap;

    private Headers() {
        this.headersMap = new LinkedHashMap<>();
    }

    public boolean isPresent() {
        return !headersMap.isEmpty();
    }

    public String[] toStringArray() {
        var arr = new String[headersMap.entrySet().size() * 2];
        var idx = new AtomicInteger(0);

        headersMap.forEach((key, value) -> {
            arr[idx.getAndIncrement()] = key;
            arr[idx.getAndIncrement()] = String.join(",", value);
        });
        return arr;
    }

    public static Headers empty() {
        return new Headers();
    }

    public static Headers of(String key, String... values) {
        if (isNullOrEmpty(key)) {
            return new Headers();
        } else if (isNullOrEmpty(values)) {
            throw new IllegalArgumentException("Empty header values are not allowed!");
        } else {
            return new Headers().with(key, List.of(values));
        }
    }

    public static Headers mergeAll(Headers... headers) {
        if (headers == null || headers.length == 0) {
            return new Headers();
        } else {
            return Stream.of(headers)
                .filter(Objects::nonNull)
                .reduce(new Headers(), Headers::merge);
        }
    }

    private void forEach(Consumer<Map.Entry<String, List<String>>> consumerFn) {
        headersMap.entrySet().forEach(consumerFn);
    }

    private Headers merge(Headers other) {
        other.forEach(entry -> merge(entry.getKey(), entry.getValue()));
        return this;
    }

    private void merge(String key, List<String> values) {
        if (headersMap.containsKey(key)) {
            var newValues = new ArrayList<String>();
            newValues.addAll(headersMap.get(key));
            newValues.addAll(values);
            headersMap.put(key, newValues);
        } else {
            headersMap.put(key, values);
        }
    }

    private Headers with(String key, List<String> values) {
        headersMap.put(key, values);
        return this;
    }

    private static boolean isNullOrEmpty(String key) {
        return key == null || key.isBlank();
    }

    private static boolean isNullOrEmpty(String... values) {
        return values == null || values.length == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Headers headers = (Headers) o;
        return Objects.equals(headersMap, headers.headersMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headersMap);
    }
}
