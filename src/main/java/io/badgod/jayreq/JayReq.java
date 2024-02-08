package io.badgod.jayreq;

import io.badgod.jayreq.impl.JayReqHttpClient;

public interface JayReq {

    static <T> Response<T> get(String url, Class<T> responseBodyType, Headers... headers) {
        return new JayReqHttpClient().get(new Request<>(url, headers), responseBodyType);
    }

    <R, T> Response<T> get(Request<R> request, Class<T> responseBodyType);

    <R, T> Response<T> post(Request<R> request, Class<T> responseBodyType);
}
