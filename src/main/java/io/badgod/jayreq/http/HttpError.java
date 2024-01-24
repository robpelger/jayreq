package io.badgod.jayreq.http;

public class HttpError extends RuntimeException {
    public HttpError(String message, Throwable cause) {
        super(message, cause);
    }
}
