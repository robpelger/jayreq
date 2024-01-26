package io.badgod.jayreq;

public class Error extends RuntimeException {
    public Error(String message, Throwable cause) {
        super(message, cause);
    }

    public static Error of(Request request, Throwable t) {
        var msg = String.format(
            "%s when executing HTTP request '%s %s'.",
            t.getClass().getSimpleName(),
            request.method,
            request.uri
        );
        return new Error(msg, t);
    }
}
