package io.badgod.jayreq;

import java.io.Serializable;
import java.net.URI;

public class Request implements Serializable {
    public final URI uri;
    public final Method method;
    public final String[] headers;

    public Request(String url, String... headers) {
        this(Method.GET, URI.create(url), headers);
    }

    public Request(Method method, URI uri, String... headers) {
        this.uri = uri;
        this.method = method;
        this.headers = validateHeaders(headers);
    }

    private static String[] validateHeaders(String... headers){
        if(headers == null || headers.length % 2 == 0) {
            return headers;
        }
        throw new IllegalArgumentException("Invalid request headers (must be multiple of 2, but was " + headers.length);
    }

}
