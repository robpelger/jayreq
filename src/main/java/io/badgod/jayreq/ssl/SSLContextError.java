package io.badgod.jayreq.ssl;

public class SSLContextError extends RuntimeException {
    public SSLContextError(String msg, Throwable cause) {
        super(msg, cause);
    }
}
