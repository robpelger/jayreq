package io.badgod.jayreq;

import java.io.Serializable;
import java.net.URI;

public class Request implements Serializable {
    private final URI uri;
    private final Method method;
    private final Body body;
    private final Headers headers;

    public Request(String url, Headers... headers) {
        this(Method.GET, URI.create(url), Body.none(), headers);
    }

    public Request(Method method, URI uri, Body body, Headers... headers) {
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

    public Body body() {
        return body;
    }

    public Headers headers() {
        return headers;
    }

    @Override
    public String toString() {
        return String.format("%s %s - Body='%s' - Headers='%s'", method, uri, body, headers);
    }
}
