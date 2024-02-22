package io.badgod;

import io.badgod.jayreq.Headers;
import io.badgod.jayreq.Request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.badgod.jayreq.Headers.of;
import static java.util.Map.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

class HeadersTest {

    @Test
    void should_have_headers_present() {
        assertThat(of("x", "y").isPresent(), is(true));
        assertThat(Headers.empty().isPresent(), is(false));
    }

    @Test
    void should_throw_if_no_header_values() {
        assertThrows(
            IllegalArgumentException.class,
            () -> of("x"));
    }

    @Test
    void should_merge_headers() {
        var merged = Headers.mergeAll(
            of("X", "value1"),
            of("Y", "value2"),
            of("X", "value3")
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

    @Test
    void should_throw_when_request_headers_not_in_pairs() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> of("X-Test"));
    }

    @Test
    void should_create_headers_from_map() {
        var map = new TreeMap<String, List<String>>();
        map.put("x", List.of("a"));
        map.put("y", List.of("b", "c"));

        var headers = of(map);

        assertThat(headers.isPresent(), is(true));
        assertThat(headers.toStringArray(), is(new String[]{"x", "a", "y", "b,c"}));
    }

    @Test
    void should_get_header_value() {
        var headers = of(Map.of(
            "x", List.of("a", "b"),
            "y", List.of("C")
        ));

        assertThat(headers.get("x").isPresent(), is(true));
        assertThat(headers.get("y").isPresent(), is(true));
        assertThat(headers.get("z").isPresent(), is(false));
        assertThat(headers.get("x").get(), is(List.of("a", "b")));
        assertThat(headers.get("y").get(), is(List.of("C")));
    }
}
