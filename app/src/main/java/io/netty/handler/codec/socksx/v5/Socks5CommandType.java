package io.netty.handler.codec.socksx.v5;

import com.integralblue.httpresponsecache.compat.libcore.net.http.HttpEngine;

public class Socks5CommandType implements Comparable<Socks5CommandType> {
    public static final Socks5CommandType BIND = new Socks5CommandType(2, "BIND");
    public static final Socks5CommandType CONNECT = new Socks5CommandType(1, HttpEngine.CONNECT);
    public static final Socks5CommandType UDP_ASSOCIATE = new Socks5CommandType(3, "UDP_ASSOCIATE");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5CommandType valueOf(byte b) {
        switch (b) {
            case (byte) 1:
                return CONNECT;
            case (byte) 2:
                return BIND;
            case (byte) 3:
                return UDP_ASSOCIATE;
            default:
                return new Socks5CommandType(b);
        }
    }

    public Socks5CommandType(int byteValue) {
        this(byteValue, "UNKNOWN");
    }

    public Socks5CommandType(int byteValue, String name) {
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
        if ((obj instanceof Socks5CommandType) && this.byteValue == ((Socks5CommandType) obj).byteValue) {
            return true;
        }
        return false;
    }

    public int compareTo(Socks5CommandType o) {
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
