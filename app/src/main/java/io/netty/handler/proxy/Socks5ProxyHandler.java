package io.netty.handler.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.socksx.v5.DefaultSocks5CommandRequest;
import io.netty.handler.codec.socksx.v5.DefaultSocks5InitialRequest;
import io.netty.handler.codec.socksx.v5.DefaultSocks5PasswordAuthRequest;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5ClientEncoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponse;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandStatus;
import io.netty.handler.codec.socksx.v5.Socks5CommandType;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequest;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponse;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponse;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthStatus;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collections;

public final class Socks5ProxyHandler extends ProxyHandler {
    private static final String AUTH_PASSWORD = "password";
    private static final Socks5InitialRequest INIT_REQUEST_NO_AUTH = new DefaultSocks5InitialRequest(Collections.singletonList(Socks5AuthMethod.NO_AUTH));
    private static final Socks5InitialRequest INIT_REQUEST_PASSWORD = new DefaultSocks5InitialRequest(Arrays.asList(new Socks5AuthMethod[]{Socks5AuthMethod.NO_AUTH, Socks5AuthMethod.PASSWORD}));
    private static final String PROTOCOL = "socks5";
    private String decoderName;
    private String encoderName;
    private final String password;
    private final String username;

    public Socks5ProxyHandler(SocketAddress proxyAddress) {
        this(proxyAddress, null, null);
    }

    public Socks5ProxyHandler(SocketAddress proxyAddress, String username, String password) {
        super(proxyAddress);
        if (username != null && username.length() == 0) {
            username = null;
        }
        if (password != null && password.length() == 0) {
            password = null;
        }
        this.username = username;
        this.password = password;
    }

    public String protocol() {
        return PROTOCOL;
    }

    public String authScheme() {
        return socksAuthMethod() == Socks5AuthMethod.PASSWORD ? AUTH_PASSWORD : "none";
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    protected void addCodec(ChannelHandlerContext ctx) throws Exception {
        ChannelPipeline p = ctx.pipeline();
        String name = ctx.name();
        Socks5InitialResponseDecoder decoder = new Socks5InitialResponseDecoder();
        p.addBefore(name, null, decoder);
        this.decoderName = p.context(decoder).name();
        this.encoderName = this.decoderName + ".encoder";
        p.addBefore(name, this.encoderName, Socks5ClientEncoder.DEFAULT);
    }

    protected void removeEncoder(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().remove(this.encoderName);
    }

    protected void removeDecoder(ChannelHandlerContext ctx) throws Exception {
        ChannelPipeline p = ctx.pipeline();
        if (p.context(this.decoderName) != null) {
            p.remove(this.decoderName);
        }
    }

    protected Object newInitialMessage(ChannelHandlerContext ctx) throws Exception {
        return socksAuthMethod() == Socks5AuthMethod.PASSWORD ? INIT_REQUEST_PASSWORD : INIT_REQUEST_NO_AUTH;
    }

    protected boolean handleResponse(ChannelHandlerContext ctx, Object response) throws Exception {
        if (response instanceof Socks5InitialResponse) {
            Socks5InitialResponse res = (Socks5InitialResponse) response;
            Socks5AuthMethod authMethod = socksAuthMethod();
            if (res.authMethod() == Socks5AuthMethod.NO_AUTH || res.authMethod() == authMethod) {
                if (authMethod == Socks5AuthMethod.NO_AUTH) {
                    sendConnectCommand(ctx);
                } else if (authMethod == Socks5AuthMethod.PASSWORD) {
                    ctx.pipeline().replace(this.decoderName, this.decoderName, new Socks5PasswordAuthResponseDecoder());
                    sendToProxyServer(new DefaultSocks5PasswordAuthRequest(this.username != null ? this.username : "", this.password != null ? this.password : ""));
                } else {
                    throw new Error();
                }
                return false;
            }
            throw new ProxyConnectException(exceptionMessage("unexpected authMethod: " + res.authMethod()));
        } else if (response instanceof Socks5PasswordAuthResponse) {
            Socks5PasswordAuthResponse res2 = (Socks5PasswordAuthResponse) response;
            if (res2.status() != Socks5PasswordAuthStatus.SUCCESS) {
                throw new ProxyConnectException(exceptionMessage("authStatus: " + res2.status()));
            }
            sendConnectCommand(ctx);
            return false;
        } else {
            Socks5CommandResponse res3 = (Socks5CommandResponse) response;
            if (res3.status() == Socks5CommandStatus.SUCCESS) {
                return true;
            }
            throw new ProxyConnectException(exceptionMessage("status: " + res3.status()));
        }
    }

    private Socks5AuthMethod socksAuthMethod() {
        if (this.username == null && this.password == null) {
            return Socks5AuthMethod.NO_AUTH;
        }
        return Socks5AuthMethod.PASSWORD;
    }

    private void sendConnectCommand(ChannelHandlerContext ctx) throws Exception {
        Socks5AddressType addrType;
        String rhost;
        InetSocketAddress raddr = (InetSocketAddress) destinationAddress();
        if (raddr.isUnresolved()) {
            addrType = Socks5AddressType.DOMAIN;
            rhost = raddr.getHostString();
        } else {
            Object rhost2 = raddr.getAddress().getHostAddress();
            if (NetUtil.isValidIpV4Address(rhost2)) {
                addrType = Socks5AddressType.IPv4;
            } else if (NetUtil.isValidIpV6Address(rhost2)) {
                addrType = Socks5AddressType.IPv6;
            } else {
                throw new ProxyConnectException(exceptionMessage("unknown address type: " + StringUtil.simpleClassName(rhost2)));
            }
        }
        ctx.pipeline().replace(this.decoderName, this.decoderName, new Socks5CommandResponseDecoder());
        sendToProxyServer(new DefaultSocks5CommandRequest(Socks5CommandType.CONNECT, addrType, rhost, raddr.getPort()));
    }
}
