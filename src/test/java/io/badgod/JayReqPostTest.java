package io.badgod;

import io.badgod.jayreq.*;
import io.badgod.jayreq.impl.JayReqHttpClient;

import org.junit.jupiter.api.Test;

import java.util.*;

import static io.badgod.jayreq.JayReq.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JayReqPostTest extends TestContainerIntegrationTest {

    @Test
    void should_do_post() {
        var resp = new JayReqHttpClient().post(new Request(testUrl("/anything")), HttpBinPostResponse.class);

        assertThat(resp.body().isPresent(), is(true));
        assertThat(resp.body().get().url(), is(testUrl("/anything")));
        assertThat(resp.body().get().method(), is("POST"));
    }

    // @formatter:off
    private record HttpBinPostResponse(String url, String method) {};
    // @formatter:on
}
