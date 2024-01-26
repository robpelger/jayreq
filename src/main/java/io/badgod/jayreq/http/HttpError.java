package io.badgod.jayreq.http;

import io.badgod.jayreq.Request;

public class HttpError extends RuntimeException {
    public HttpError(String message, Throwable cause) {
        super(message, cause);
    }

    public static HttpError of(Request request, Throwable t) {
        var msg = String.format(
            "%s when executing HTTP request '%s %s'.",
            t.getClass().getSimpleName(),
            request.method,
            request.uri
        );
        return new HttpError(msg, t);
    }
}
