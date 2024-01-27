package io.badgod.jayreq.impl;

import io.badgod.jayreq.Response;

class ConversionError extends RuntimeException {
    private final Response<String> rawResponse;

    ConversionError(Response<String> rawResponse, Throwable cause) {
        super(cause);
        this.rawResponse = rawResponse;
    }

    Response<String> rawResponse() {
        return rawResponse;
    }
}
