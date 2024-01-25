package io.badgod;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;

public abstract class TestContainerIntegrationTest {
    protected static GenericContainer<?> container;

    @BeforeAll
    public static void startTestContainer() {
        try(var c = new GenericContainer<>("kennethreitz/httpbin:latest")) {
            container = c.withExposedPorts(80);
        }
        container.start();
    }

    @AfterAll
    public static void stopTestContainer() {
        container.stop();
        container.close();
    }

    protected static String testUrl(String path) {
        return String.format("http://%s:%s%s",
            container.getHost(),
            container.getFirstMappedPort(),
            path);
    }
}
