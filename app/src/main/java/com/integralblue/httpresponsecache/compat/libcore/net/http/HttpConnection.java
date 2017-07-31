package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.URIs;
import com.integralblue.httpresponsecache.compat.libcore.io.IoUtils;
import com.integralblue.httpresponsecache.compat.libcore.util.Objects;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

final class HttpConnection {
    private final Address address;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean recycled;
    private final Socket socket;
    private InputStream sslInputStream;
    private OutputStream sslOutputStream;
    private SSLSocket sslSocket;
    private SSLSocket unverifiedSocket;

    public static final class Address {
        private final Proxy proxy;
        private final boolean requiresTunnel;
        private final String socketHost;
        private final int socketPort;
        private final SSLSocketFactory sslSocketFactory;
        private final String uriHost;
        private final int uriPort;

        public Address(URI uri, SSLSocketFactory sslSocketFactory) throws UnknownHostException {
            this.proxy = null;
            this.requiresTunnel = false;
            this.uriHost = uri.getHost();
            this.uriPort = URIs.getEffectivePort(uri);
            this.sslSocketFactory = sslSocketFactory;
            this.socketHost = this.uriHost;
            this.socketPort = this.uriPort;
            if (this.uriHost == null) {
                throw new UnknownHostException(uri.toString());
            }
        }

        public Address(URI uri, SSLSocketFactory sslSocketFactory, Proxy proxy, boolean requiresTunnel) throws UnknownHostException {
            this.proxy = proxy;
            this.requiresTunnel = requiresTunnel;
            this.uriHost = uri.getHost();
            this.uriPort = URIs.getEffectivePort(uri);
            this.sslSocketFactory = sslSocketFactory;
            SocketAddress proxyAddress = proxy.address();
            if (proxyAddress instanceof InetSocketAddress) {
                InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
                this.socketHost = proxySocketAddress.getHostName();
                this.socketPort = proxySocketAddress.getPort();
                if (this.uriHost == null) {
                    throw new UnknownHostException(uri.toString());
                }
                return;
            }
            throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
        }

        public Proxy getProxy() {
            return this.proxy;
        }

        public boolean equals(Object other) {
            if (!(other instanceof Address)) {
                return false;
            }
            Address that = (Address) other;
            if (Objects.equal(this.proxy, that.proxy) && this.uriHost.equals(that.uriHost) && this.uriPort == that.uriPort && this.requiresTunnel == that.requiresTunnel) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int hashCode;
            int i = 0;
            int hashCode2 = (((this.uriHost.hashCode() + 527) * 31) + this.uriPort) * 31;
            if (this.sslSocketFactory != null) {
                hashCode = this.sslSocketFactory.hashCode();
            } else {
                hashCode = 0;
            }
            hashCode2 = (hashCode2 + hashCode) * 31;
            if (this.proxy != null) {
                hashCode = this.proxy.hashCode();
            } else {
                hashCode = 0;
            }
            hashCode = (hashCode2 + hashCode) * 31;
            if (this.requiresTunnel) {
                i = 1;
            }
            return hashCode + i;
        }

        public HttpConnection connect(int connectTimeout) throws IOException {
            return new HttpConnection(this, connectTimeout);
        }
    }

    private HttpConnection(Address config, int connectTimeout) throws IOException {
        this.recycled = false;
        this.address = config;
        Socket socketCandidate = null;
        InetAddress[] addresses = InetAddress.getAllByName(config.socketHost);
        int i = 0;
        while (i < addresses.length) {
            socketCandidate = (config.proxy == null || config.proxy.type() == Type.HTTP) ? new Socket() : new Socket(config.proxy);
            try {
                socketCandidate.connect(new InetSocketAddress(addresses[i], config.socketPort), connectTimeout);
                break;
            } catch (IOException e) {
                if (i == addresses.length - 1) {
                    throw e;
                }
                i++;
            }
        }
        this.socket = socketCandidate;
    }

    public static HttpConnection connect(URI uri, SSLSocketFactory sslSocketFactory, Proxy proxy, boolean requiresTunnel, int connectTimeout) throws IOException {
        if (proxy != null) {
            return HttpConnectionPool.INSTANCE.get(proxy.type() == Type.DIRECT ? new Address(uri, sslSocketFactory) : new Address(uri, sslSocketFactory, proxy, requiresTunnel), connectTimeout);
        }
        ProxySelector selector = ProxySelector.getDefault();
        List<Proxy> proxyList = selector.select(uri);
        if (proxyList != null) {
            for (Proxy selectedProxy : proxyList) {
                if (selectedProxy.type() != Type.DIRECT) {
                    try {
                        return HttpConnectionPool.INSTANCE.get(new Address(uri, sslSocketFactory, selectedProxy, requiresTunnel), connectTimeout);
                    } catch (IOException e) {
                        selector.connectFailed(uri, selectedProxy.address(), e);
                    }
                }
            }
        }
        return HttpConnectionPool.INSTANCE.get(new Address(uri, sslSocketFactory), connectTimeout);
    }

    public void closeSocketAndStreams() {
        IoUtils.closeQuietly(this.sslOutputStream);
        IoUtils.closeQuietly(this.sslInputStream);
        IoUtils.closeQuietly(this.sslSocket);
        IoUtils.closeQuietly(this.outputStream);
        IoUtils.closeQuietly(this.inputStream);
        IoUtils.closeQuietly(this.socket);
    }

    public void setSoTimeout(int readTimeout) throws SocketException {
        this.socket.setSoTimeout(readTimeout);
    }

    public OutputStream getOutputStream() throws IOException {
        if (this.sslSocket != null) {
            if (this.sslOutputStream == null) {
                this.sslOutputStream = this.sslSocket.getOutputStream();
            }
            return this.sslOutputStream;
        }
        if (this.outputStream == null) {
            this.outputStream = this.socket.getOutputStream();
        }
        return this.outputStream;
    }

    public InputStream getInputStream() throws IOException {
        if (this.sslSocket != null) {
            if (this.sslInputStream == null) {
                this.sslInputStream = this.sslSocket.getInputStream();
            }
            return this.sslInputStream;
        }
        if (this.inputStream == null) {
            this.inputStream = this.address.requiresTunnel ? this.socket.getInputStream() : new BufferedInputStream(this.socket.getInputStream(), 128);
        }
        return this.inputStream;
    }

    protected Socket getSocket() {
        return this.sslSocket != null ? this.sslSocket : this.socket;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setupSecureSocket(SSLSocketFactory sslSocketFactory, boolean tlsTolerant) throws IOException {
        this.unverifiedSocket = (SSLSocket) sslSocketFactory.createSocket(this.socket, this.address.uriHost, this.address.uriPort, true);
        if (tlsTolerant) {
            try {
                Class<?> socketClass = this.unverifiedSocket.getClass();
                Method method = socketClass.getMethod("setEnabledCompressionMethods", new Class[]{String[].class});
                SSLSocket sSLSocket = this.unverifiedSocket;
                Object[] objArr = new Object[1];
                objArr[0] = new String[]{"ZLIB"};
                method.invoke(sSLSocket, objArr);
                socketClass.getMethod("setUseSessionTickets", new Class[]{Boolean.TYPE}).invoke(this.unverifiedSocket, new Object[]{Boolean.valueOf(true)});
                socketClass.getMethod("setHostname", new Class[]{String.class}).invoke(this.unverifiedSocket, new Object[]{this.address.socketHost});
            } catch (Exception e) {
                this.unverifiedSocket.setEnabledProtocols(new String[]{"SSLv3"});
            }
        } else {
            this.unverifiedSocket.setEnabledProtocols(new String[]{"SSLv3"});
        }
        this.unverifiedSocket.startHandshake();
    }

    public SSLSocket verifySecureSocketHostname(HostnameVerifier hostnameVerifier) throws IOException {
        if (hostnameVerifier.verify(this.address.uriHost, this.unverifiedSocket.getSession())) {
            this.sslSocket = this.unverifiedSocket;
            return this.sslSocket;
        }
        throw new IOException("Hostname '" + this.address.uriHost + "' was not verified");
    }

    public SSLSocket getSecureSocketIfConnected() {
        return this.sslSocket;
    }

    public boolean isRecycled() {
        return this.recycled;
    }

    public void setRecycled() {
        this.recycled = true;
    }

    protected boolean isEligibleForRecycling() {
        return (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) ? false : true;
    }
}
