package io.netty.handler.codec.http.cors;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public final class CorsConfigBuilder {
    boolean allowCredentials;
    boolean allowNullOrigin;
    final boolean anyOrigin;
    boolean enabled;
    final Set<String> exposeHeaders;
    long maxAge;
    private boolean noPreflightHeaders;
    final Set<String> origins;
    final Map<CharSequence, Callable<?>> preflightHeaders;
    final Set<String> requestHeaders;
    final Set<HttpMethod> requestMethods;
    boolean shortCircuit;

    private static final class ConstantValueGenerator implements Callable<Object> {
        private final Object value;

        private ConstantValueGenerator(Object value) {
            if (value == null) {
                throw new IllegalArgumentException("value must not be null");
            }
            this.value = value;
        }

        public Object call() {
            return this.value;
        }
    }

    private static final class DateValueGenerator implements Callable<Date> {
        static final DateValueGenerator INSTANCE = new DateValueGenerator();

        private DateValueGenerator() {
        }

        public Date call() throws Exception {
            return new Date();
        }
    }

    public static CorsConfigBuilder forAnyOrigin() {
        return new CorsConfigBuilder();
    }

    public static CorsConfigBuilder forOrigin(String origin) {
        if (WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD.equals(origin)) {
            return new CorsConfigBuilder();
        }
        return new CorsConfigBuilder(origin);
    }

    public static CorsConfigBuilder forOrigins(String... origins) {
        return new CorsConfigBuilder(origins);
    }

    CorsConfigBuilder(String... origins) {
        this.enabled = true;
        this.exposeHeaders = new HashSet();
        this.requestMethods = new HashSet();
        this.requestHeaders = new HashSet();
        this.preflightHeaders = new HashMap();
        this.origins = new LinkedHashSet(Arrays.asList(origins));
        this.anyOrigin = false;
    }

    CorsConfigBuilder() {
        this.enabled = true;
        this.exposeHeaders = new HashSet();
        this.requestMethods = new HashSet();
        this.requestHeaders = new HashSet();
        this.preflightHeaders = new HashMap();
        this.anyOrigin = true;
        this.origins = Collections.emptySet();
    }

    public CorsConfigBuilder allowNullOrigin() {
        this.allowNullOrigin = true;
        return this;
    }

    public CorsConfigBuilder disable() {
        this.enabled = false;
        return this;
    }

    public CorsConfigBuilder exposeHeaders(String... headers) {
        this.exposeHeaders.addAll(Arrays.asList(headers));
        return this;
    }

    public CorsConfigBuilder allowCredentials() {
        this.allowCredentials = true;
        return this;
    }

    public CorsConfigBuilder maxAge(long max) {
        this.maxAge = max;
        return this;
    }

    public CorsConfigBuilder allowedRequestMethods(HttpMethod... methods) {
        this.requestMethods.addAll(Arrays.asList(methods));
        return this;
    }

    public CorsConfigBuilder allowedRequestHeaders(String... headers) {
        this.requestHeaders.addAll(Arrays.asList(headers));
        return this;
    }

    public CorsConfigBuilder preflightResponseHeader(CharSequence name, Object... values) {
        if (values.length == 1) {
            this.preflightHeaders.put(name, new ConstantValueGenerator(values[0]));
        } else {
            preflightResponseHeader(name, Arrays.asList(values));
        }
        return this;
    }

    public <T> CorsConfigBuilder preflightResponseHeader(CharSequence name, Iterable<T> value) {
        this.preflightHeaders.put(name, new ConstantValueGenerator(value));
        return this;
    }

    public <T> CorsConfigBuilder preflightResponseHeader(CharSequence name, Callable<T> valueGenerator) {
        this.preflightHeaders.put(name, valueGenerator);
        return this;
    }

    public CorsConfigBuilder noPreflightResponseHeaders() {
        this.noPreflightHeaders = true;
        return this;
    }

    public CorsConfigBuilder shortCircuit() {
        this.shortCircuit = true;
        return this;
    }

    public CorsConfig build() {
        if (this.preflightHeaders.isEmpty() && !this.noPreflightHeaders) {
            this.preflightHeaders.put(HttpHeaderNames.DATE, DateValueGenerator.INSTANCE);
            this.preflightHeaders.put(HttpHeaderNames.CONTENT_LENGTH, new ConstantValueGenerator("0"));
        }
        return new CorsConfig(this);
    }
}
