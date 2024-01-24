package io.badgod.jayreq;

import io.badgod.jayreq.http.HttpMethod;

import java.net.URI;

public class Request {
    public final URI uri;
    public final HttpMethod method;
    public final String[] headers;

    public Request(String url, String... headers) {
        this(URI.create(url), HttpMethod.GET, headers);
    }

    private Request(URI uri, HttpMethod method, String... headers) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
    }

}
