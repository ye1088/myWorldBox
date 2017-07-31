package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.nio.charset.CharsetEncoder;

public final class SocksAuthRequest extends SocksRequest {
    private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
    private static final CharsetEncoder asciiEncoder = CharsetUtil.encoder(CharsetUtil.US_ASCII);
    private final String password;
    private final String username;

    public SocksAuthRequest(String username, String password) {
        super(SocksRequestType.AUTH);
        if (username == null) {
            throw new NullPointerException("username");
        } else if (password == null) {
            throw new NullPointerException("username");
        } else if (!asciiEncoder.canEncode(username) || !asciiEncoder.canEncode(password)) {
            throw new IllegalArgumentException("username: " + username + " or password: **** values should be in pure ascii");
        } else if (username.length() > 255) {
            throw new IllegalArgumentException("username: " + username + " exceeds 255 char limit");
        } else if (password.length() > 255) {
            throw new IllegalArgumentException("password: **** exceeds 255 char limit");
        } else {
            this.username = username;
            this.password = password;
        }
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
        byteBuf.writeByte(this.username.length());
        byteBuf.writeBytes(this.username.getBytes(CharsetUtil.US_ASCII));
        byteBuf.writeByte(this.password.length());
        byteBuf.writeBytes(this.password.getBytes(CharsetUtil.US_ASCII));
    }
}
