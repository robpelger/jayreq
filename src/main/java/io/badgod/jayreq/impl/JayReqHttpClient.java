package io.badgod.jayreq.impl;

import io.badgod.jayreq.*;

import com.google.gson.Gson;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class JayReqHttpClient implements JayReq {

    private final HttpClient client;
    private final Gson gson;

    public JayReqHttpClient() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public <T> Response<T> get(Request request, Class<T> responseBodyType) {
        return this.execute(request, responseBodyType);
    }

    @Override
    public <T> Response<T> post(Request request, Class<T> responseBodyType) {
        var postRequest = new Request(Method.POST, request.uri, request.headers);
        return this.execute(postRequest, responseBodyType);
    }

    private <T> Response<T> execute(Request request, Class<T> resultType) {
        try {
            Response<String> rawResponse = doExecute(request);
            return convert(rawResponse, resultType);
        } catch (ExecutionError e) {
            throw new JayReqError(request, e);
        } catch (ConversionError e) {
            throw new JayReqError(request, e.rawResponse(), e);
        }
    }

    private Response<String> doExecute(Request request) {
        try {
            var httpResp = client.send(createRequest(request), BodyHandlers.ofString(StandardCharsets.UTF_8));
            return new Response<>(httpResp.body(), httpResp.statusCode(), httpResp.headers().map());
        } catch (Exception e) {
            throw new ExecutionError(e);
        }
    }

    private <T> Response<T> convert(Response<String> rawResponse, Class<T> resultType) {
        try {
            var convertedBodyOrNull = rawResponse.body()
                .map(rawBody -> gson.fromJson(rawBody, resultType))
                .orElse(null);
            return new Response<>(
                    convertedBodyOrNull,
                    rawResponse.status(),
                    rawResponse.headers());
        } catch (Exception e) {
            throw new ConversionError(rawResponse, e);
        }
    }


    private static HttpRequest createRequest(Request request) {
        var builder = HttpRequest.newBuilder().uri(request.uri);

        builder = switch (request.method) {
            case Method.GET -> builder.GET();
            case Method.POST -> builder.POST(HttpRequest.BodyPublishers.noBody());
        };

        if (request.headers != null && request.headers.length > 0) {
            builder = builder.headers(request.headers);
        }

        return builder.build();
    }
}
