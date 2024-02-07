package io.badgod.jayreq;

import java.util.Optional;

public class JayReqError extends RuntimeException {
    private final Request<?> request;
    private final Response<String> rawResponse;

    public JayReqError(Request<?> request, Throwable cause) {
        this(request, null, cause);
    }

    public JayReqError(Request<?> request, Response<String> rawResponse, Throwable cause) {
        super(createMessage(request, cause), cause);
        this.request = request;
        this.rawResponse = rawResponse;
    }

    public Request<?> request() {
        return request;
    }

    public Optional<Response<String>> rawResponse() {
        return Optional.ofNullable(rawResponse);
    }

    private static String createMessage(Request<?> request, Throwable cause) {
        return String.format(
            "%s on HTTP request '%s %s': %s",
            cause.getClass().getSimpleName(),
            request.method(),
            request.uri(),
            cause.getCause().getClass().getSimpleName()
        );
    }
}
