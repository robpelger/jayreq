package io.badgod.jayreq.ssl;

import org.apache.hc.core5.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

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

    public static SSLContext createFor(KeystoreConfig... configs) {
        if (configs == null || configs.length == 0) {
            return null;
        }
        var builder = SSLContextBuilder.create();
        try {
            for (var config : configs) {
                builder = loadKeystore(config, builder);
            }
            return builder.build();
        } catch (Exception e) {
            throw new SSLContextError("Error initialising SSL-Context", e);
        }
    }

    private static SSLContextBuilder loadKeystore(KeystoreConfig config, SSLContextBuilder builder) throws Exception {
        try (InputStream in = new FileInputStream(config.filename())) {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(in, config.password().toCharArray());

            builder = builder
                .loadKeyMaterial(keystore, config.password().toCharArray())
                .loadTrustMaterial(keystore, (chain, authType) -> true);
        }
        return builder;
    }

}
