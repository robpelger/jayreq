package io.badgod.jayreq.ssl;

import org.apache.hc.core5.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class SSLContextFactory {

    public static SSLContext create(String keystoreFile, String keystorePassword) {
        try (InputStream in = new FileInputStream(keystoreFile)) {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(in, keystorePassword.toCharArray());
            return SSLContextBuilder.create()
                .loadKeyMaterial(keystore, keystorePassword.toCharArray())
                .loadTrustMaterial(keystore, (chain, authType) -> true)
                .build();
        } catch (Exception e) {
            throw new SSLContextError("Error initialising SSL-Context", e);
        }
    }

}
