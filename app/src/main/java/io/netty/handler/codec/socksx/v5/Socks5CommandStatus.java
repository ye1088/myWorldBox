package io.netty.handler.codec.socksx.v5;

public class Socks5CommandStatus implements Comparable<Socks5CommandStatus> {
    public static final Socks5CommandStatus ADDRESS_UNSUPPORTED = new Socks5CommandStatus(8, "ADDRESS_UNSUPPORTED");
    public static final Socks5CommandStatus COMMAND_UNSUPPORTED = new Socks5CommandStatus(7, "COMMAND_UNSUPPORTED");
    public static final Socks5CommandStatus CONNECTION_REFUSED = new Socks5CommandStatus(5, "CONNECTION_REFUSED");
    public static final Socks5CommandStatus FAILURE = new Socks5CommandStatus(1, "FAILURE");
    public static final Socks5CommandStatus FORBIDDEN = new Socks5CommandStatus(2, "FORBIDDEN");
    public static final Socks5CommandStatus HOST_UNREACHABLE = new Socks5CommandStatus(4, "HOST_UNREACHABLE");
    public static final Socks5CommandStatus NETWORK_UNREACHABLE = new Socks5CommandStatus(3, "NETWORK_UNREACHABLE");
    public static final Socks5CommandStatus SUCCESS = new Socks5CommandStatus(0, "SUCCESS");
    public static final Socks5CommandStatus TTL_EXPIRED = new Socks5CommandStatus(6, "TTL_EXPIRED");
    private final byte byteValue;
    private final String name;
    private String text;

    public static Socks5CommandStatus valueOf(byte b) {
        switch (b) {
            case (byte) 0:
                return SUCCESS;
            case (byte) 1:
                return FAILURE;
            case (byte) 2:
                return FORBIDDEN;
            case (byte) 3:
                return NETWORK_UNREACHABLE;
            case (byte) 4:
                return HOST_UNREACHABLE;
            case (byte) 5:
                return CONNECTION_REFUSED;
            case (byte) 6:
                return TTL_EXPIRED;
            case (byte) 7:
                return COMMAND_UNSUPPORTED;
            case (byte) 8:
                return ADDRESS_UNSUPPORTED;
            default:
                return new Socks5CommandStatus(b);
        }
    }

    public Socks5CommandStatus(int byteValue) {
        this(byteValue, "UNKNOWN");
    }

    public Socks5CommandStatus(int byteValue, String name) {
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
        if ((obj instanceof Socks5CommandStatus) && this.byteValue == ((Socks5CommandStatus) obj).byteValue) {
            return true;
        }
        return false;
    }

    public int compareTo(Socks5CommandStatus o) {
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
