package io.netty.handler.codec.haproxy;

public enum HAProxyProtocolVersion {
    V1((byte) 16),
    V2((byte) 32);
    
    private static final byte VERSION_MASK = (byte) -16;
    private final byte byteValue;

    private HAProxyProtocolVersion(byte byteValue) {
        this.byteValue = byteValue;
    }

    public static HAProxyProtocolVersion valueOf(byte verCmdByte) {
        int version = verCmdByte & -16;
        switch ((byte) version) {
            case (byte) 16:
                return V1;
            case (byte) 32:
                return V2;
            default:
                throw new IllegalArgumentException("unknown version: " + version);
        }
    }

    public byte byteValue() {
        return this.byteValue;
    }
}
