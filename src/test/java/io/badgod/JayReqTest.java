package io.badgod;

import io.badgod.jayreq.JayReq;
import io.badgod.jayreq.Request;
import io.badgod.jayreq.http.JayReqHttpClient;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JayReqTest {

    @Test
    void should_do_get_request_via_shortcut() {
        var response = JayReq.get(
                "https://api.github.com/repos/robpelger/jayreq",
                GithubRepoResponse.class);

        assertThat(
                response.body().description(),
                is("JayReq is a batteries-included HTTP client for Java."));
    }

    @Test
    void should_do_get_request_via_instance() {
        JayReq jayreq = new JayReqHttpClient();
        var response = jayreq.get(
            new Request("https://api.github.com/repos/robpelger/jayreq"),
            GithubRepoResponse.class);

        assertThat(
            response.body().description(),
            is("JayReq is a batteries-included HTTP client for Java."));
    }

    private record GithubRepoResponse(String description) {
    }
}
