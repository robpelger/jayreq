package io.badgod.jayreq;

import java.io.Serializable;
import java.util.*;

public class Response<T> implements Serializable {
    private final T body;
    private final int status;
    private final Map<String, List<String>> headers;

    public Response(T body, int status, Map<String, List<String>> headers) {
        this.body = body;
        this.status = status;
        this.headers = headers;
    }

    public Optional<T> body() {
        return Optional.ofNullable(body);
    }

    public int status() {
        return status;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }
}
