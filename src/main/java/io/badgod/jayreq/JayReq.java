package io.badgod.jayreq;

import io.badgod.jayreq.impl.JayReqHttpClient;

public interface JayReq {

    static <T> Response<T> get(String url, Class<T> responseBodyType, String... headers) {
        return new JayReqHttpClient().get(new Request(url, headers), responseBodyType);
    }

    <T> Response<T> get(Request request, Class<T> responseBodyType);
}
