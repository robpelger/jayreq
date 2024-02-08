package io.badgod;

import io.badgod.jayreq.Headers;

import org.junit.jupiter.api.Test;

import static io.badgod.jayreq.Headers.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

public class HeadersTest {

    @Test
    void should_have_headers_present() {
        assertThat(Headers.of("x", "y").isPresent(), is(true));
        assertThat(Headers.empty().isPresent(), is(false));
    }

    @Test
    void should_throw_if_no_header_values() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Headers.of("x"));
    }

    @Test
    void should_merge_headers() {
        var merged = Headers.mergeAll(
            Headers.of("X", "value1"),
            Headers.of("Y", "value2"),
            Headers.of("X", "value3")
        );
        assertThat(
            merged.toStringArray(),
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
