package io.badgod;

import io.badgod.jayreq.*;
import io.badgod.jayreq.impl.JayReqHttpClient;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JayReqPostTest extends TestContainerIntegrationTest {

    @Test
    void should_do_post() {
        Request<?> req = new Request<>(testUrl("/anything"));
        var resp = new JayReqHttpClient().post(req, HttpBinPostResponse.class);

        assertThat(resp.body().isPresent(), is(true));
        assertThat(resp.body().get().url(), is(testUrl("/anything")));
        assertThat(resp.body().get().method(), is("POST"));
    }

    @Test
    void should_do_post_with_body() {
        var req = new Request<>(
            Method.POST,
            testUri("/anything"),
            "some-body",
            Headers.of("X-Header1", "header-1-value")
        );
        var res = new JayReqHttpClient().post(req, HttpBinPostResponse.class);

        assertThat(res.body().isPresent(), is(true));
        assertThat(res.body().get().url(), is(testUrl("/anything")));
        assertThat(res.body().get().method(), is("POST"));
        assertThat(res.body().get().data(), is("some-body"));
    }

    // @formatter:off
    private record HttpBinPostResponse(String url, String method, String data, String json) {};
    // @formatter:on
}
