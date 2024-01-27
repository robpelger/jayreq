package io.badgod.jayreq;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record Response<T>(
    T body,
    Map<String, List<String>> headers) implements Serializable {
}
