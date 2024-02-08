package io.badgod.jayreq;

import java.io.Serializable;
import java.net.URI;
import java.util.*;
import java.util.stream.Stream;

public class Request<T> implements Serializable {
    private final URI uri;
    private final Method method;
    private final T body;
    private final Headers headers;

    public Request(String url, Headers... headers) {
        this(Method.GET, URI.create(url), null, headers);
    }

    public Request(Method method, URI uri, T body, Headers... headers) {
        this.uri = uri;
        this.method = method;
        this.body = body;
        this.headers = merge(headers);
    }

    public URI uri() {
        return uri;
    }

    public Method method() {
        return method;
    }

    public Optional<T> body() {
        return Optional.ofNullable(body);
    }

    public Headers headers() {
        return headers;
    }

    private static Headers merge(Headers... headers) {
        if (headers == null || headers.length == 0) {
            return new Headers();
        } else {
            return Stream.of(headers)
                .filter(Objects::nonNull)
                .reduce(
                new Headers(),
                (acc, next) -> {
                    next.forEach((key, values) -> {
                        if (acc.containsKey(key)) {
                            List<String> merged = new ArrayList<>();
                            merged.addAll(acc.get(key));
                            merged.addAll(values);
                            acc.put(key, merged);
                        } else {
                            acc.put(key, values);
                        }
                    });
                    return acc;
                });
        }
    }
}
