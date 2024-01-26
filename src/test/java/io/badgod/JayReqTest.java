package io.badgod;

import io.badgod.jayreq.JayReq;
import io.badgod.jayreq.Request;
import io.badgod.jayreq.Error;
import io.badgod.jayreq.impl.JayReqHttpClient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JayReqTest extends TestContainerIntegrationTest {

    @Test
    void should_do_get_request_via_shortcut() {
        var resp = JayReq.get(testUrl("/anything"), HttpBinGetResponse.class);
        assertThat(resp.body().url(), is(testUrl("/anything")));
        assertThat(resp.body().method(), is("GET"));
    }

    @Test
    void should_do_get_request_via_instance() {
        JayReq jr = new JayReqHttpClient();
        var resp = jr.get(new Request(testUrl("/anything")), HttpBinGetResponse.class);
        assertThat(resp.body().url(), is(testUrl("/anything")));
        assertThat(resp.body().method(), is("GET"));
    }

    @Test
    void should_contain_response_headers() {
        var resp = JayReq.get(testUrl("/headers"), HttpBinGetResponse.class);
        assertThat(resp.headers().entrySet(), is(not(empty())));

        // access to headers (i.e. the header keys) is case-insensitive!
        assertThat(resp.headers().get("Content-Type"), is(List.of("application/json")));
        assertThat(resp.headers().get("CONTENT-TYPE"), is(List.of("application/json")));
        assertThat(resp.headers().get("content-type"), is(List.of("application/json")));
    }

    @Test
    void should_send_request_headers() {
        var resp = JayReq.get(
            testUrl("/headers"),
            HttpBinHeadersResponse.class,
            "Authorization", "Bearer xyz",
            "X-Test", "Hello",
            "X-Test", "World!");

        //The request headers are returned in the body in field "headers"
        //see: https://httpbin.org/#/Request_inspection/get_headers
        assertThat(resp.body().headers().entrySet(), is(not(empty())));
        assertThat(resp.body().headers().get("Authorization"), is("Bearer xyz"));
        assertThat(resp.body().headers().get("X-Test"), is("Hello,World!"));
    }

    @Test
    void should_throw_when_request_headers_not_in_pairs() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Request("http://test", "X-Test"));
    }

    @Test
    void should_throw_on_connection_error() {
        assertThrows(Error.class, () -> JayReq.get("http://localhost", String.class));
    }

    private record HttpBinGetResponse(String url, String method){};
    private record HttpBinHeadersResponse(Map<String, String> headers){};
}
