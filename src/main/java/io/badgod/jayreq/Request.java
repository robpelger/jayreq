package io.badgod.jayreq;

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;

public class Request<T> implements Serializable {
    private final URI uri;
    private final Method method;
    private final T body;
    private final String[] headers;

    public Request(String url, String... headers) {
        this(Method.GET, URI.create(url), null, headers);
    }

    public Request(Method method, URI uri, T body, String... headers) {
        this.uri = uri;
        this.method = method;
        this.body = body;
        this.headers = validateHeaders(headers);
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

    public String[] headers() {
        return headers;
    }

    private static String[] validateHeaders(String... headers){
        if(headers == null || headers.length % 2 == 0) {
            return headers;
        }
        throw new IllegalArgumentException("Invalid request headers (must be multiple of 2, but was " + headers.length);
    }

}
