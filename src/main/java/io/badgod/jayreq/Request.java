package io.badgod.jayreq;

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;

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
        this.headers = Headers.mergeAll(headers);
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
}
