package io.badgod.jayreq.error;

import io.badgod.jayreq.Request;

public class ExecutionError extends RuntimeException {
    private final Request request;

    public ExecutionError(Request request, Throwable cause) {
        super(cause);
        this.request = request;
    }

    public Request request() {
        return request;
    }
}
