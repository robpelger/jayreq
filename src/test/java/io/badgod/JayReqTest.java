package io.badgod;

import io.badgod.jayreq.*;
import io.badgod.jayreq.impl.JayReqHttpClient;

import org.junit.jupiter.api.Test;

import java.util.*;

import static io.badgod.jayreq.JayReq.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JayReqTest extends TestContainerIntegrationTest {

    @Test
    void should_do_get_request_via_shortcut() {
        var resp = get(testUrl("/anything"), HttpBinGetResponse.class);

        assertThat(resp.body().isPresent(), is(true));
        assertThat(resp.body().get().url(), is(testUrl("/anything")));
        assertThat(resp.body().get().method(), is("GET"));
    }

    @Test
    void should_do_get_request_via_instance() {
        JayReq jr = new JayReqHttpClient();
        var resp = jr.get(new Request(testUrl("/anything")), HttpBinGetResponse.class);

        assertThat(resp.body().isPresent(), is(true));
        assertThat(resp.body().get().url(), is(testUrl("/anything")));
        assertThat(resp.body().get().method(), is("GET"));
    }

    @Test
    void should_contain_response_headers() {
        var resp = get(testUrl("/headers"), HttpBinGetResponse.class);
        assertThat(resp.headers().entrySet(), is(not(empty())));

        // access to headers (i.e. the header keys) is case-insensitive!
        assertThat(resp.headers().get("Content-Type"), is(List.of("application/json")));
        assertThat(resp.headers().get("CONTENT-TYPE"), is(List.of("application/json")));
        assertThat(resp.headers().get("content-type"), is(List.of("application/json")));
    }

    @Test
    void should_contain_empty_body_when_there_is_no_body_in_http_response() {
        var resp = get(testUrl("/status/200"), Object.class);

        assertThat(resp.body().isEmpty(), is(true));
        assertThat(resp.headers(), is(not(anEmptyMap())));
    }

    @Test
    void should_send_request_headers() {
        var resp = get(
            testUrl("/headers"),
            HttpBinHeadersResponse.class,
            "Authorization", "Bearer xyz",
            "X-Test", "Hello",
            "X-Test", "World!");

        //The request headers are returned in the body in field "headers"
        //see: https://httpbin.org/#/Request_inspection/get_headers
        assertThat(resp.body().isPresent(), is(true));
        assertThat(resp.body().get().headers().entrySet(), is(not(empty())));
        assertThat(resp.body().get().headers().get("Authorization"), is("Bearer xyz"));
        assertThat(resp.body().get().headers().get("X-Test"), is("Hello,World!"));
    }

    @Test
    void should_contain_status_in_response() {
        assertThat(JayReq.get(testUrl("/status/200"), Object.class).status(), is(200));
        assertThat(JayReq.get(testUrl("/status/503"), Object.class).status(), is(503));
        assertThat(JayReq.get(testUrl("/status/429"), Object.class).status(), is(429));
        assertThat(JayReq.get(testUrl("/status/302101"), Object.class).status(), is(302));
    }

    @Test
    void should_throw_when_request_headers_not_in_pairs() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Request("http://test", "X-Test"));
    }

    @Test
    void should_throw_on_connection_error() {
        assertThrows(JayReqError.class, () -> get("http://localhost", String.class));
    }

    @Test
    void should_throw_on_failed_json_mapping() {
        String url = testUrl("/anything");
        assertThrows(JayReqError.class, () -> get(url, HttpBinGetResponseInvalid.class));
    }

    @Test
    void should_be_able_to_inspect_request_and_raw_response_on_failed_json_mapping() {
        try {
            get(testUrl("/anything"), HttpBinGetResponseInvalid.class);
        } catch (JayReqError err) {
            assertThat(err.rawResponse().isPresent(), is(true));
            assertThat(err.rawResponse().get().body(), is(not(Optional.empty())));
            assertThat(err.rawResponse().get().headers(), is(not(anEmptyMap())));
            assertThat(err.request(), is(not(nullValue())));
            assertThat(err.request().headers, is(not(nullValue())));
            assertThat(err.request().method, is(Method.GET));
            assertThat(err.request().uri.toString(), is(testUrl("/anything")));
        }
    }

    // @formatter:off
    private record HttpBinGetResponse(String url, String method) {};
    private record HttpBinGetResponseInvalid(int url, int method) {};
    private record HttpBinHeadersResponse(Map<String, String> headers) {};
    // @formatter:on
}
