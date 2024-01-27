package io.badgod.jayreq;

import java.io.Serializable;
import java.net.URI;

public class Request implements Serializable {
    public final URI uri;
    public final Method method;
    public final String[] headers;

    public Request(String url, String... headers) {
        this(URI.create(url), Method.GET, headers);
    }

    private Request(URI uri, Method method, String... headers) {
        this.method = method;
        this.uri = uri;
        this.headers = validateHeaders(headers);
    }

    private static String[] validateHeaders(String... headers){
        if(headers == null || headers.length % 2 == 0) {
            return headers;
        }
        throw new IllegalArgumentException("Invalid request headers (must be multiple of 2, but was " + headers.length);
    }

}
