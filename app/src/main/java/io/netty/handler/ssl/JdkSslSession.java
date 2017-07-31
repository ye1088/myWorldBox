package io.netty.handler.ssl;

import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

final class JdkSslSession implements ApplicationProtocolAccessor, SSLSession {
    private volatile String applicationProtocol;
    private final SSLEngine engine;

    JdkSslSession(SSLEngine engine) {
        this.engine = engine;
    }

    private SSLSession unwrap() {
        return this.engine.getSession();
    }

    public String getProtocol() {
        return unwrap().getProtocol();
    }

    public String getApplicationProtocol() {
        return this.applicationProtocol;
    }

    void setApplicationProtocol(String applicationProtocol) {
        this.applicationProtocol = applicationProtocol;
    }

    public byte[] getId() {
        return unwrap().getId();
    }

    public SSLSessionContext getSessionContext() {
        return unwrap().getSessionContext();
    }

    public long getCreationTime() {
        return unwrap().getCreationTime();
    }

    public long getLastAccessedTime() {
        return unwrap().getLastAccessedTime();
    }

    public void invalidate() {
        unwrap().invalidate();
    }

    public boolean isValid() {
        return unwrap().isValid();
    }

    public void putValue(String s, Object o) {
        unwrap().putValue(s, o);
    }

    public Object getValue(String s) {
        return unwrap().getValue(s);
    }

    public void removeValue(String s) {
        unwrap().removeValue(s);
    }

    public String[] getValueNames() {
        return unwrap().getValueNames();
    }

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return unwrap().getPeerCertificates();
    }

    public Certificate[] getLocalCertificates() {
        return unwrap().getLocalCertificates();
    }

    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return unwrap().getPeerCertificateChain();
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return unwrap().getPeerPrincipal();
    }

    public Principal getLocalPrincipal() {
        return unwrap().getLocalPrincipal();
    }

    public String getCipherSuite() {
        return unwrap().getCipherSuite();
    }

    public String getPeerHost() {
        return unwrap().getPeerHost();
    }

    public int getPeerPort() {
        return unwrap().getPeerPort();
    }

    public int getPacketBufferSize() {
        return unwrap().getPacketBufferSize();
    }

    public int getApplicationBufferSize() {
        return unwrap().getApplicationBufferSize();
    }
}
