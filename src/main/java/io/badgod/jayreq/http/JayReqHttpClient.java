package io.badgod.jayreq.http;

import io.badgod.jayreq.*;
import io.badgod.jayreq.Request;
import io.badgod.jayreq.Response;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class JayReqHttpClient implements JayReq {

    private final HttpClient client;

    public JayReqHttpClient() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public <T> Response<T> get(Request request, Class<T> responseBodyType) {
        return this.execute(request, responseBodyType);
    }

    private <T> Response<T> execute(Request req, Class<T> resultType) {
        try {
            var httpResponse = client.send(createRequest(req), new HttpJsonBodyHandler<>(resultType));
            return new Response<>(httpResponse.body(), httpResponse.headers().map());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HttpError("Error executing HTTP call", e);
        } catch (Exception e) {
            throw new HttpError("Error executing HTTP call", e);
        }
    }

    private static HttpRequest createRequest(Request request) {
        var builder = HttpRequest.newBuilder().uri(request.uri);

        builder = switch (request.method) {
            case HttpMethod.GET -> builder.GET();
        };

        if (request.headers != null && request.headers.length > 0) {
            builder = builder.headers(request.headers);
        }

        return builder.build();
    }
}
