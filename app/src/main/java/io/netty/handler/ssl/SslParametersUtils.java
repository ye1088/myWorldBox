package io.netty.handler.ssl;

import java.security.AlgorithmConstraints;
import javax.net.ssl.SSLParameters;

final class SslParametersUtils {
    private SslParametersUtils() {
    }

    static void setAlgorithmConstraints(SSLParameters sslParameters, Object algorithmConstraints) {
        sslParameters.setAlgorithmConstraints((AlgorithmConstraints) algorithmConstraints);
    }
}
