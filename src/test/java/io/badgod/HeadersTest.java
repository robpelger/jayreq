package io.badgod;

import io.badgod.jayreq.*;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static io.badgod.jayreq.Headers.of;
import static io.badgod.jayreq.Method.POST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

public class HeadersTest {

    @Test
    void should_create_empty_headers_in_request() {
        assertThat(new Request<>("http://x", (Headers[]) null).headers(), is(new Headers()));
        assertThat(new Request<>("http://x", (Headers) null).headers(), is(new Headers()));
        assertThat(new Request<>("http://x").headers(), is(new Headers()));
        assertThat(new Request<>("http://x", Headers.of(null)).headers(), is(new Headers()));
    }

    @Test
    void should_have_headers_present() {
        assertThat(new Request<>("http://x", of("x", "y")).headers().isPresent(), is(true));
        assertThat(new Request<>("http://x").headers().isPresent(), is(false));
    }

    @Test
    void should_throw_if_no_header_values() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Request<>("http://x", of("x")));
    }

    @Test
    void should_merge_headers() {
        Request<Object> req = new Request<>(
            "url",
            of("X", "value1"),
            of("Y", "value2"),
            of("X", "value3")
        );
        assertThat(
            req.headers().toStringArray(),
            is(new String[]{"X", "value1,value3", "Y", "value2"}));
    }

    @Test
    void should_flatten_to_array() {
        var arr = of("X-Test", "a", "b").toStringArray();
        assertThat(arr.length, is(2));
        assertThat(arr[0], is("X-Test"));
        assertThat(arr[1], is("a,b"));
    }
}
