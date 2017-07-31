package io.netty.handler.codec.socksx.v5;

public class Socks5PasswordAuthStatus implements Comparable<Socks5PasswordAuthStatus> {
    public static final Socks5PasswordAuthStatus FAILURE = new Socks5PasswordAuthStatus(255, "FAILURE");
    public static final Socks5PasswordAuthStatus SUCCESS = new Socks5PasswordAuthStatus(0, "SUCCESS");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5PasswordAuthStatus valueOf(byte b) {
        switch (b) {
            case (byte) -1:
                return FAILURE;
            case (byte) 0:
                return SUCCESS;
            default:
                return new Socks5PasswordAuthStatus(b);
        }
    }

    public Socks5PasswordAuthStatus(int byteValue) {
        this(byteValue, "UNKNOWN");
    }

    public Socks5PasswordAuthStatus(int byteValue, String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.byteValue = (byte) byteValue;
        this.name = name;
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public boolean isSuccess() {
        return this.byteValue == (byte) 0;
    }

    public int hashCode() {
        return this.byteValue;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof Socks5PasswordAuthStatus) && this.byteValue == ((Socks5PasswordAuthStatus) obj).byteValue) {
            return true;
        }
        return false;
    }

    public int compareTo(Socks5PasswordAuthStatus o) {
        return this.byteValue - o.byteValue;
    }

    public String toString() {
        String text = this.text;
        if (text != null) {
            return text;
        }
        text = this.name + '(' + (this.byteValue & 255) + ')';
        this.text = text;
        return text;
    }
}
