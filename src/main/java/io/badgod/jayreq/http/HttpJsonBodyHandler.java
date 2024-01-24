package io.badgod.jayreq.http;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.http.HttpResponse;

public class HttpJsonBodyHandler<T> implements HttpResponse.BodyHandler<T>{

    private final Gson gson;
    private final Class<T> resultType;

    public HttpJsonBodyHandler(Class<T> resultType) {
        this.gson = new Gson();
        this.resultType = resultType;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(
            HttpResponse.BodySubscribers.ofInputStream(),
            inputStream -> gson.fromJson(new InputStreamReader(inputStream), resultType)
        );
    }
}
