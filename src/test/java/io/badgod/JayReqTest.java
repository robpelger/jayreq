package io.badgod;

import io.badgod.jayreq.JayReq;
import io.badgod.jayreq.Request;
import io.badgod.jayreq.http.HttpError;
import io.badgod.jayreq.http.JayReqHttpClient;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JayReqTest {

    private final static String GH_URL = "https://api.github.com/repos/robpelger/jayreq";
    public static final String EXPECTED_DESCRIPTION = "JayReq is a batteries-included HTTP client for Java.";

    @Test
    void should_do_get_request_via_shortcut() {
        var resp = JayReq.get(GH_URL, GithubRepoResponse.class);
        assertThat(resp.body().description(), is(EXPECTED_DESCRIPTION));
    }

    @Test
    void should_do_get_request_via_instance() {
        JayReq jr = new JayReqHttpClient();
        var resp = jr.get(new Request(GH_URL), GithubRepoResponse.class);
        assertThat(resp.body().description(), is(EXPECTED_DESCRIPTION));
    }

    @Test
    void should_throw_on_connection_error() {
        assertThrows(HttpError.class, () -> JayReq.get("http://does-not.exist", String.class));
    }

    private record GithubRepoResponse(String description) {
    }
}
