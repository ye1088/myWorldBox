package io.netty.channel.local;

import io.netty.channel.Channel;
import java.net.SocketAddress;

public final class LocalAddress extends SocketAddress implements Comparable<LocalAddress> {
    public static final LocalAddress ANY = new LocalAddress("ANY");
    private static final long serialVersionUID = 4644331421130916435L;
    private final String id;
    private final String strVal;

    LocalAddress(Channel channel) {
        StringBuilder buf = new StringBuilder(16);
        buf.append("local:E");
        buf.append(Long.toHexString((((long) channel.hashCode()) & 4294967295L) | 4294967296L));
        buf.setCharAt(7, ':');
        this.id = buf.substring(6);
        this.strVal = buf.toString();
    }

    public LocalAddress(String id) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        id = id.trim().toLowerCase();
        if (id.isEmpty()) {
            throw new IllegalArgumentException("empty id");
        }
        this.id = id;
        this.strVal = "local:" + id;
    }

    public String id() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof LocalAddress) {
            return this.id.equals(((LocalAddress) o).id);
        }
        return false;
    }

    public int compareTo(LocalAddress o) {
        return this.id.compareTo(o.id);
    }

    public String toString() {
        return this.strVal;
    }
}
