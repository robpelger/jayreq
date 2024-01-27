package io.badgod.jayreq.error;

import io.badgod.jayreq.Response;

public class ConversionError extends RuntimeException {
    private final Response<String> rawResponse;

    public ConversionError(Response<String> rawResponse, Throwable cause) {
        super(cause);
        this.rawResponse = rawResponse;
    }

    public Response<String> rawResponse() {
        return rawResponse;
    }
}
