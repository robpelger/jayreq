package io.badgod.jayreq;

import java.io.Serializable;
import java.util.*;

public class Body implements Serializable {
    private final String value;

    private Body(String body) {
        this.value = body;
    }

    public Optional<String> value() {
        return Optional.ofNullable(value);
    }

    public boolean isEmpty() {
        return value == null;
    }

    public static Body none() {
        return new Body(null);
    }

    public static Body of(String body) {
        return body == null || body.isBlank()
            ? new Body(null)
            : new Body(body);
    }

    public interface Converter<T> {
        T apply(int status, Map<String, List<String>> headers, String body);
    }

    @Override
    public String toString() {
        return value;
    }
}
