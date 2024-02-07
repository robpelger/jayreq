package io.badgod.jayreq;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Response<T>  implements Serializable {
    private final T body;
    private final Map<String, List<String>> headers;

    public Response(T body, Map<String, List<String>> headers) {
        this.body = body;
        this.headers = headers;
    }

    public Optional<T> body() {
        return Optional.ofNullable(body);
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

    public <R> Response<R> map(BiFunction<T, Map<String, List<String>>, Response<R>> mapperFn) {
        return mapperFn.apply(this.body, this.headers);
    }
}
