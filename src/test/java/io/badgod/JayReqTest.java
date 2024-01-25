package io.badgod;

import io.badgod.jayreq.JayReq;
import io.badgod.jayreq.Request;
import io.badgod.jayreq.http.HttpError;
import io.badgod.jayreq.http.JayReqHttpClient;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JayReqTest extends TestContainerIntegrationTest {

    @Test
    void should_do_get_request_via_shortcut() {
        var resp = JayReq.get(testUrl("/anything"), HttpBinResponse.class);
        assertThat(resp.body().url(), is(testUrl("/anything")));
        assertThat(resp.body().method(), is("GET"));
    }

    @Test
    void should_do_get_request_via_instance() {
        JayReq jr = new JayReqHttpClient();
        var resp = jr.get(new Request(testUrl("/anything")), HttpBinResponse.class);
        assertThat(resp.body().url(), is(testUrl("/anything")));
        assertThat(resp.body().method(), is("GET"));
    }

    @Test
    void should_throw_on_connection_error() {
        assertThrows(HttpError.class, () -> JayReq.get("http://localhost", String.class));
    }

    private record HttpBinResponse(String url, String method) {
    }
}
