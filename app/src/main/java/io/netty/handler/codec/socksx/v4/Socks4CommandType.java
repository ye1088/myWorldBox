package io.netty.handler.codec.socksx.v4;

import com.integralblue.httpresponsecache.compat.libcore.net.http.HttpEngine;

public class Socks4CommandType implements Comparable<Socks4CommandType> {
    public static final Socks4CommandType BIND = new Socks4CommandType(2, "BIND");
    public static final Socks4CommandType CONNECT = new Socks4CommandType(1, HttpEngine.CONNECT);
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks4CommandType valueOf(byte b) {
        switch (b) {
            case (byte) 1:
                return CONNECT;
            case (byte) 2:
                return BIND;
            default:
                return new Socks4CommandType(b);
        }
    }

    public Socks4CommandType(int byteValue) {
        this(byteValue, "UNKNOWN");
    }

    public Socks4CommandType(int byteValue, String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.byteValue = (byte) byteValue;
        this.name = name;
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public int hashCode() {
        return this.byteValue;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof Socks4CommandType) && this.byteValue == ((Socks4CommandType) obj).byteValue) {
            return true;
        }
        return false;
    }

    public int compareTo(Socks4CommandType o) {
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
