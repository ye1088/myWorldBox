package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;

public class DefaultSocks5PasswordAuthRequest extends AbstractSocks5Message implements Socks5PasswordAuthRequest {
    private final String password;
    private final String username;

    public DefaultSocks5PasswordAuthRequest(String username, String password) {
        if (username == null) {
            throw new NullPointerException("username");
        } else if (password == null) {
            throw new NullPointerException("password");
        } else if (username.length() > 255) {
            throw new IllegalArgumentException("username: **** (expected: less than 256 chars)");
        } else if (password.length() > 255) {
            throw new IllegalArgumentException("password: **** (expected: less than 256 chars)");
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

    public String toString() {
        StringBuilder buf = new StringBuilder(StringUtil.simpleClassName((Object) this));
        DecoderResult decoderResult = decoderResult();
        if (decoderResult.isSuccess()) {
            buf.append("(username: ");
        } else {
            buf.append("(decoderResult: ");
            buf.append(decoderResult);
            buf.append(", username: ");
        }
        buf.append(username());
        buf.append(", password: ****)");
        return buf.toString();
    }
}
