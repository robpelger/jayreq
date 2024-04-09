package io.badgod.jayreq;

import java.io.Serializable;
import java.util.*;

public class Response implements Serializable {
    private final Request request;
    private final Body body;
    private final int status;
    private final Headers headers;

    public Response(Request request, String body, int status, Map<String, List<String>> headers) {
        this.request = request;
        this.body = Body.of(body);
        this.status = status;
        this.headers = Headers.of(headers);
    }

    /**
     * If a body is present in this response, the given body-converter function offers you a way to convert the raw body
     * (String) to any object you like. The converter function will have access to the response headers.
     *
     * @param bodyConverter (responseHeaders, responseBodyString) -> convertedBodyAsObject
     * @param <T>           type of the body after applying converter function
     * @return convertedBodyAsObject
     */
    public <T> Optional<T> body(Body.Converter<T> bodyConverter) {
        return body.value().map(rawBody -> bodyConverter.apply(status, headers, rawBody));
    }

    public Request request() {
        return request;
    }

    public Body body() {
        return body;
    }

    public int status() {
        return status;
    }

    public Headers headers() {
        return headers;
    }

    @Override
    public String toString() {
        return  String.format("Status: %s%nBody: %s%nHeaders: %s", status, body, headers);
    }
}
